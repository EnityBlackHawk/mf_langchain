package org.mf.langchain

import org.springframework.core.io.PathResource
import org.springframework.core.io.support.EncodedResource
import org.springframework.jdbc.datasource.init.ScriptUtils
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException

class DataImporter {

    companion object{

//        fun importAirlines(filePath: String, airlineRepository: AirlineRepository?) {
//            val jsonFile = File(filePath)
//            val jsonString = jsonFile.readText();
//            val gson = Gson();
//            val itemType = object : TypeToken<List<AirlineJson>>() {}.type
//            val airlineJson = gson.fromJson<List<AirlineJson>>(jsonString, itemType)
//
//            airlineRepository?.apply {
//                saveAll(
//                        airlineJson.map { it ->
//                            Airline.builder()
//                                    .id(it.id)
//                                    .iata(it.iata)
//                                    .icao(it.icao)
//                                    .name(it.name)
//                                    .build()
//                        }
//                )
//            }
//        }
//
//        fun importAirports(filePath : String, airportRepository: AirportRepository?) : String {
//            val jsonFile = File(filePath)
//            val jsonString = jsonFile.readText();
//            val gson = Gson();
//            val itemType = object  : TypeToken<List<AirportJson>>() {}.type
//            val airportJson = gson.fromJson<List<AirportJson>>(jsonString, itemType)
//
//           airportRepository?.apply {
//               saveAll(
//                       airportJson.map {it ->
//                           Airport.builder()
//                                   .id(it.iata_code)
//                                   .city(it.city)
//                                   .country(it.country)
//                                   .name(it.name)
//                                   .build()
//                       }
//               )
//           }
//           return airportJson[0].iata_code
//        }
        fun runSQLFromFile(path : String, connection : Connection) {
            ScriptUtils.executeSqlScript(
                connection,
                EncodedResource(PathResource(path)),
                false,
                false,
                ScriptUtils.DEFAULT_COMMENT_PREFIX,
                ScriptUtils.DEFAULT_STATEMENT_SEPARATOR,
                ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER,
                ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER
            );
        }

        fun runSQL(sql : String, connection : Connection) : String {
            var result: String;
            try {
                connection.createStatement().use { statement ->
                    val hasRS = statement.execute(sql)
                    result = if (hasRS) resResultSet(statement.resultSet) else "OK"
                    statement.close()
                }
            } catch (e : SQLException){
                return e.message.toString()
            }
            return result
        }

        fun createDatabase(connection : Connection, databaseName : String) : String {
            return runSQL("CREATE DATABASE $databaseName", connection)
        }

        private fun resResultSet(rs : ResultSet) : String {
            val sb = StringBuilder()
            val md = rs.metaData
            val columns = md.columnCount
            val ss = mutableListOf<String>()
            for (i in 1..columns) {
                ss.add(md.getColumnName(i))
            }

            var formatString = ""
            for(i in 1 .. columns)
                formatString += "%15s "
            formatString += "\n"

            sb.append(String.format(formatString, *ss.toTypedArray()))
            while (rs.next()) {
                val row = mutableListOf<String>()
                for (i in 1..columns) {
                    row.add(rs.getString(i))
                }
                sb.append(String.format(formatString, *row.toTypedArray()))
            }
            return sb.toString()
        }
    }



}