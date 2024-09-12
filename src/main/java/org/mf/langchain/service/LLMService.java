package org.mf.langchain.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;
import org.jetbrains.annotations.Nullable;
import org.mf.langchain.ChatAssistant;
import org.mf.langchain.ConvertToJavaFile;
import org.mf.langchain.DTO.*;
import org.mf.langchain.DataImporter;
import org.mf.langchain.MockLayer;
import org.mf.langchain.auto.*;
import org.mf.langchain.enums.ProcessStepName;
import org.mf.langchain.exception.DBConnectionException;
import org.mf.langchain.metadata.Column;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.DTO.RelationCardinality;
import org.mf.langchain.metadata.Table;
import org.mf.langchain.model.BasicRuns;
import org.mf.langchain.prompt.*;
import org.mf.langchain.util.QueryResult;
import org.mf.langchain.util.TemplatedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.*;

@Service
public class LLMService {

    private final PersistenceService persistenceService;
    private final PassengerRepository passengerRepository;
    private final AirlineRepository airlineRepository;
    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final BookingRepository bookingRepository;

    private final BasicRunsService basicRunsService;

    public LLMService(@Autowired PersistenceService persistenceService, @Autowired PassengerRepository passengerRepository, AirlineRepository airlineRepository, FlightRepository flightRepository, AircraftRepository aircraftRepository, AirportRepository airportRepository, BookingRepository bookingRepository, BasicRunsService basicRunsService){
        this.persistenceService = persistenceService;
        this.passengerRepository = passengerRepository;
        this.airlineRepository = airlineRepository;
        this.flightRepository = flightRepository;
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
        this.bookingRepository = bookingRepository;
        this.basicRunsService = basicRunsService;
    }

    public MfEntity<?> initFlow(SpecificationDTO spec) {
        var data = getData(spec);
        var mtInfo = new MetadataInfoDTO(data.getFirst(), data.getSecond());
        if(spec.isOnline())
            return new MfEntity<>(ProcessStepName.GENERATE_MODEL, mtInfo);

        return generateModel(new GenerateSpecsDTO(spec, mtInfo));
    }

    public MfEntity<ModelDTO> generateModel(GenerateSpecsDTO gSpecs) {
        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(gSpecs.getSpecification().LLM())
                .maxRetries(1)
                .temperature(1d)
                .build();
        var gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();

        var prompt = new PromptData3(
                gSpecs.getMetadataInfo().getSql(),
                gSpecs.getSpecification().prioritize_performance() ? MigrationPreferences.PREFER_PERFORMANCE : MigrationPreferences.PREFER_CONSISTENCY,
                gSpecs.getSpecification().allow_ref(),
                Framework.valueOf(gSpecs.getSpecification().framework()),
                gSpecs.getMetadataInfo().getRelations().toString(),
                true,
                gSpecs.getSpecification().workload().stream().map(Query::new).toList(),
                gSpecs.getSpecification().custom_prompt()

        );
        var p = prompt.next();
        int tokens = 0;
        String resultString;
        ArrayList<String> objs = new ArrayList<>();

        if(MockLayer.isActivated) {
            resultString = MockLayer.MOCK_GENERATE_MODEL;
        }
        else {
            var result = gptAssistant.chat(p);
            tokens = result.tokenUsage().totalTokenCount();
            resultString = result.content().text();
        }

        while (true) {
            int iS = resultString.indexOf("```json");
            if(iS == -1) break;
            iS += 7;
            int iE = resultString.indexOf("```", iS);
            objs.add(resultString.substring(iS, iE));
            resultString = resultString.substring(iE + 3);
        }



//        persistenceService.persist(
//                new TestResultDTO(
//                        prompt.get(),
//                        SpecificationDTO.overrideCardinality(SpecificationDTO.overrideDataSource(gSpecs.getSpecification(), gSpecs.getMetadataInfo().getSql()), gSpecs.getMetadataInfo().getRelations()),
//                        result.content().text(),
//                        result.tokenUsage().totalTokenCount()
//                )
//        );

        var finalResult = objs.stream().reduce("", String::concat);
        System.out.println(finalResult);

        return new MfEntity<>(ProcessStepName.GENERATE_JAVA_CODE, new ModelDTO(finalResult, tokens));
    }

    public MfEntity<?> generateJavaCode(ModelDTO model) {
        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName("gpt-3.5-turbo")
                .maxRetries(1)
                .temperature(1d)
                .build();
        var gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();
        int token = 0;
        String result;

        if(MockLayer.isActivated) {
            result = MockLayer.MOCK_GENERATE_JAVA_CODE;
        }
        else {
            var prompt = PromptData3.getSecond(model.getModel(), Framework.SPRING_DATA);
            var res = gptAssistant.chat(prompt);
            result = res.content().text();
            token = res.tokenUsage().totalTokenCount();
            System.out.println(result);
        }

        return new MfEntity<String>(ProcessStepName.FINAL, result);

    }

    public LLMResponse runBasic(Credentials credentials) throws SQLException {
        var mdb = new DbMetadata(credentials.getConnectionString(), credentials.getUsername(), credentials.getPassword(), null);
        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName("gpt-4o-mini")
                .maxRetries(1)
                .temperature(1d)
                .build();
        var gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();
        var prompt = new PromptData(mdb, MigrationPreferences.PREFER_PERFORMANCE, false, Framework.SPRING_DATA, null, null);
        var text = prompt.get();
        var res = gptAssistant.chat(text);

        var brh = new BasicRuns(null, prompt.get(), res.content().text());
        basicRunsService.create(brh);

        ConvertToJavaFile.toFile("src/main/java/org/mf/langchain/auto/", "org.mf.langchain.auto",res.content().text());
        return new LLMResponse(res.content().text(), res.tokenUsage().totalTokenCount(), prompt.get(), new Date());
    }

    public LLMResponse reRunBasic() {
        var brh = basicRunsService.getLast();
        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName("gpt-4o-mini")
                .maxRetries(1)
                .temperature(1d)
                .build();
        var gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();
        var res = gptAssistant.chat(brh.getPrompt());
        return new LLMResponse(res.content().text(), res.tokenUsage().totalTokenCount(), brh.getPrompt(), new Date());
    }

    public LLMResponse Generate(SpecificationDTO spec) {

        var data = getData(spec);

        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(spec.LLM())
                .maxRetries(1)
                .temperature(0.5)
                .build();
        var gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();

        var prompt = new PromptData3(
                        data.getFirst(),
                        spec.prioritize_performance() ? MigrationPreferences.PREFER_PERFORMANCE : MigrationPreferences.PREFER_CONSISTENCY,
                        spec.allow_ref(),
                        Framework.valueOf(spec.framework()),
                        data.getSecond().toString(),
                        true,
                        spec.workload().stream().map(Query::new).toList(),
                        spec.custom_prompt()
                        );
        System.out.println(prompt.get());

        var result = gptAssistant.chat(prompt.get());
        //var result = "Response";

        persistenceService.persist(
                new TestResultDTO(
                        prompt.get(),
                        SpecificationDTO.overrideCardinality(SpecificationDTO.overrideDataSource(spec, data.getFirst()), data.getSecond()),
                        result.content().text(),
                        result.tokenUsage().totalTokenCount()
                )
        );
        return new LLMResponse(
                result.content().text(),
                result.tokenUsage().totalTokenCount(),
                prompt.get(),
                new Date());
    }

    public List<Relations> getRelations(String text, @Nullable ChatAssistant gptAssistant) {
        if(gptAssistant == null)
        {
            var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                    .apiKey(System.getenv("GPT_KEY"))
                    .modelName(OpenAiChatModelName.GPT_3_5_TURBO)
                    .maxRetries(1)
                    .logRequests(true)
                    .logResponses(true)
                    //.responseFormat("json_object")
                    .temperature(0.5)
                    .build();
            gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();
        }

        var q = "Considering this database: \n" + text + " What are the relations between the tables? (Remove duplicates)";
        Type listType = new TypeToken<List<Relations>>() {}.getType();
        String response = gptAssistant.getRelations(q);
        Gson gson = new Gson();
        return gson.fromJson(response, listType);
    }

    public List<RelationCardinality> getRelationsCardinality(DbMetadata metadata) {

        var queries = new ArrayList<Pair<Relations, String>>();

        var rels = getRelations(metadata.toString(), null);
        //var rels = List.of(new Relations("aircraft", "airline", "many-to-one"));

        List<RelationCardinality> rcd = new ArrayList<>();

        var templateString = new TemplatedString(
                        "SELECT {{target}}.{{target_pk}} AS id , " +
                        "COUNT({{getSource}}.{{source_pk}}) AS number_of_{{getSource}} " +
                        "FROM {{getSource}} " +
                        "JOIN {{target}} ON {{getSource}}.{{prop}} = {{target}}.{{target_pk}} " +
                        "GROUP BY {{target}}.{{target_pk}}"
        );

        for(var rel : rels) {

            if(rel.cardinality.equals("one-to-many")) continue;
            if(rel.table_source.equals(rel.table_target)) continue;

            Table tSource = metadata.getTables().stream().filter((e) -> Objects.equals(e.name(), rel.table_source))
                    .findFirst().orElseThrow(RuntimeException::new);
            List<Column.FkInfo> props = tSource.columns().stream().filter((e) -> e.isFk() && Objects.equals(e.fkInfo().pk_tableName(), rel.table_target))
                    .map(Column::fkInfo).toList();

            if(props.isEmpty())
                throw new RuntimeException("No foreign key found");

            queries.addAll(
                    props.stream().map((e) -> Pair.of(rel, templateString.render(
                            Pair.of("getSource", rel.table_source),
                            Pair.of("target", rel.table_target),
                            Pair.of("target_pk", e.pk_name()),
                            Pair.of("source_pk", tSource.getPrimaryKey().name()),
                            Pair.of("prop", e.columnName()))
                    )).toList()
            );
        }

        for(var q : queries) {
            var qResult =
                    DataImporter.Companion.runQuery(q.getSecond(), metadata.getConnection(), QueryResult.class);
            var values = qResult.getAllFromColumn("number_of_" + q.getFirst().table_source, Integer.class);
            int min = values.stream().min(Integer::compareTo).orElseThrow(RuntimeException::new);
            int max = values.stream().max(Integer::compareTo).orElseThrow(RuntimeException::new);
            double avg = values.stream().mapToInt(Integer::intValue).average().orElseThrow();
            rcd.add(new RelationCardinality(q.getFirst(), min, max, avg));
        }

        return rcd;
    }

    private Pair<String, List<RelationCardinality>> getData(SpecificationDTO spec) {
        String data = "";
        List<RelationCardinality> card = null;

        if(spec.data_source() == null) {
            
            if(spec.credentials() == null)
                throw new RuntimeException("Credentials are required when not proving data getSource");
            
            if(spec.credentials().getConnectionString() == null)
                throw new RuntimeException("credentials.connectionString is required when not proving data getSource");
            
            var cred = spec.credentials();
            var user = cred.getUsername() != null ? cred.getUsername() : "admin";
            var passw = cred.getPassword() != null ? cred.getPassword() : "admin";
            try {
                var mdb = new DbMetadata(
                        spec.credentials().getConnectionString(),
                        user,
                        passw,
                        null);

                if(!mdb.isConnected())
                    throw new DBConnectionException();
                data = mdb.toString();
                //card = DataImporter.Companion.getCardinality(mdb.getConnection());
                if(spec.cardinality() == null)
                    card = getRelationsCardinality(mdb);
                else card = spec.cardinality();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            data = spec.data_source();
            card = spec.cardinality();
        }
        if(card == null) throw new RuntimeException("Cardinality was null");
        return Pair.of(data, card);
    }

    public void pushMigration() throws SQLException {
        var dbm = new DbMetadata("jdbc:postgresql://localhost:5432/airport3", "admin", "admin", null);

        var qr = DataImporter.Companion.runQuery("SELECT * FROM airline", dbm, QueryResult.class);
        List<Airline> airlines = qr.asObject(Airline.class);
        airlineRepository.saveAll(airlines);

        var qr_passengers = DataImporter.Companion.runQuery("SELECT * FROM passenger", dbm, QueryResult.class);
        List<Passenger> passengers = qr_passengers.asObject(Passenger.class);
        passengerRepository.saveAll(passengers);

        var qr_flights = DataImporter.Companion.runQuery("SELECT * FROM flight", dbm, QueryResult.class);
        List<Flight> flights = qr_flights.asObject(Flight.class);
        flightRepository.saveAll(flights);

        var qr_aircraft = DataImporter.Companion.runQuery("SELECT * FROM aircraft", dbm, QueryResult.class);
        List<Aircraft> aircrafts = qr_aircraft.asObject(Aircraft.class);
        aircraftRepository.saveAll(aircrafts);

        var qr_airports = DataImporter.Companion.runQuery("SELECT * FROM airport", dbm,
                QueryResult.class);
        List<Airport> airports = qr_airports.asObject(Airport.class);
        airportRepository.saveAll(airports);

        var qr_bookings = DataImporter.Companion.runQuery("SELECT * FROM booking", dbm, QueryResult.class);
        List<Booking> bookings = qr_bookings.asObject(Booking.class);
        bookingRepository.saveAll(bookings);
    }
}
