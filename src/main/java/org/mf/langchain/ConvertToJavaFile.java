package org.mf.langchain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConvertToJavaFile {

    public static String MOCK = "```java\n" +
            "import lombok.Data;\n" +
            "import org.springframework.data.annotation.Id;\n" +
            "import org.springframework.data.mongodb.core.mapping.Document;\n" +
            "\n" +
            "@Data\n" +
            "@Document(collection = \"flightData\")\n" +
            "public class FlightData {\n" +
            "    @Id\n" +
            "    private String id;\n" +
            "    private List<Flight> flights;\n" +
            "}\n" +
            "\n" +
            "@Data\n" +
            "public class Flight {\n" +
            "    private String number;\n" +
            "    private int gate;\n" +
            "    private Date arrivalTimeScheduled;\n" +
            "    private Date arrivalTimeActual;\n" +
            "    private Date departureTimeScheduled;\n" +
            "    private Date departureTimeActual;\n" +
            "    private Airport airportTo;\n" +
            "    private Airport airportFrom;\n" +
            "    private Flight connectsTo;\n" +
            "    private Aircraft aircraft;\n" +
            "    private Airline airline;\n" +
            "}\n" +
            "\n" +
            "@Data\n" +
            "public class Airport {\n" +
            "    private String id;\n" +
            "    private String name;\n" +
            "    private String city;\n" +
            "    private String country;\n" +
            "}\n" +
            "\n" +
            "@Data\n" +
            "public class Aircraft {\n" +
            "    private int id;\n" +
            "    private int airlineId;\n" +
            "    private int manufacturerId;\n" +
            "    private int maxPassengers;\n" +
            "    private String registration;\n" +
            "    private String type;\n" +
            "}\n" +
            "\n" +
            "@Data\n" +
            "public class Airline {\n" +
            "    private int id;\n" +
            "    private String iata;\n" +
            "    private String icao;\n" +
            "    private String name;\n" +
            "}\n" +
            "\n" +
            "@Data\n" +
            "public class Manufacturer {\n" +
            "    private int id;\n" +
            "    private String name;\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "These Java classes are structured with Lombok annotations for getter, setter, and constructor generation. The classes are suitable for use with the Spring Data MongoDB framework, with embedded documents for Airport, Aircraft, Airline, and Manufacturer within the Flight object.";

    public static String MOCK_2 = "```java\n" +
            "import org.springframework.data.annotation.Id;\n" +
            "import org.springframework.data.mongodb.core.mapping.DBRef;\n" +
            "import org.springframework.data.mongodb.core.mapping.Document;\n" +
            "\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "@Document\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class Aircraft {\n" +
            "    @Id\n" +
            "    private Integer id;\n" +
            "    \n" +
            "    @DBRef\n" +
            "    private Airline airline;\n" +
            "\n" +
            "    @DBRef\n" +
            "    private Manufacturer manufacturer;\n" +
            "\n" +
            "    private Integer maxPassengers;\n" +
            "    private String registration;\n" +
            "    private String type;\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "```java\n" +
            "import org.springframework.data.annotation.Id;\n" +
            "import org.springframework.data.mongodb.core.mapping.Document;\n" +
            "\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "@Document\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class Airline {\n" +
            "    @Id\n" +
            "    private Integer id;\n" +
            "    private String iata;\n" +
            "    private String icao;\n" +
            "    private String name;\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "```java\n" +
            "import org.springframework.data.annotation.Id;\n" +
            "import org.springframework.data.mongodb.core.mapping.Document;\n" +
            "\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "@Document\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class Airport {\n" +
            "    @Id\n" +
            "    private String id;\n" +
            "    private String city;\n" +
            "    private String country;\n" +
            "    private String name;\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "```java\n" +
            "import org.springframework.data.annotation.Id;\n" +
            "import org.springframework.data.mongodb.core.mapping.DBRef;\n" +
            "import org.springframework.data.mongodb.core.mapping.Document;\n" +
            "\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "@Document\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class Flight {\n" +
            "    @Id\n" +
            "    private String number;\n" +
            "    \n" +
            "    @DBRef\n" +
            "    private Aircraft aircraft;\n" +
            "\n" +
            "    private Integer gate;\n" +
            "    private String arrivalTimeScheduled;\n" +
            "    private String arrivalTimeActual;\n" +
            "    private String departureTimeScheduled;\n" +
            "    private String departureTimeActual;\n" +
            "\n" +
            "    @DBRef\n" +
            "    private Airport airportFrom;\n" +
            "\n" +
            "    @DBRef\n" +
            "    private Airport airportTo;\n" +
            "\n" +
            "    @DBRef\n" +
            "    private Flight connectsTo;\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "```java\n" +
            "import org.springframework.data.annotation.Id;\n" +
            "import org.springframework.data.mongodb.core.mapping.Document;\n" +
            "\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "@Document\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class Manufacturer {\n" +
            "    @Id\n" +
            "    private Integer id;\n" +
            "    private String name;\n" +
            "}\n" +
            "```";

    public static void toFile(String path, String content) {

        var contents = content.split("```\n\n```java");

        contents[0] = contents[0].substring(8);
        contents[contents.length -1] = contents[contents.length -1].substring(0, contents[contents.length -1].indexOf('`'));

        try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path + "classes.java"));
                for(String c : contents)
                    writer.write(c);
                writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
