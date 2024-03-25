package org.mf.langchain

import com.google.gson.Gson
import org.mf.langchain.DTO.AirportJson
import org.mf.langchain.repositories.AirportRepository
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.reflect.TypeToken;
import org.mf.langchain.DTO.AirlineJson
import org.mf.langchain.model.Airline
import org.mf.langchain.model.Airport
import org.mf.langchain.repositories.AirlineRepository

class DataImporter {

    companion object{

        fun importAirlines(filePath: String, airlineRepository: AirlineRepository?) {
            val jsonFile = File(filePath)
            val jsonString = jsonFile.readText();
            val gson = Gson();
            val itemType = object : TypeToken<List<AirlineJson>>() {}.type
            val airlineJson = gson.fromJson<List<AirlineJson>>(jsonString, itemType)

            airlineRepository?.apply {
                saveAll(
                        airlineJson.map { it ->
                            Airline.builder()
                                    .id(it.id)
                                    .iata(it.iata)
                                    .icao(it.icao)
                                    .name(it.name)
                                    .build()
                        }
                )
            }
        }

        fun importAirports(filePath : String, airportRepository: AirportRepository?) : String {
            val jsonFile = File(filePath)
            val jsonString = jsonFile.readText();
            val gson = Gson();
            val itemType = object  : TypeToken<List<AirportJson>>() {}.type
            val airportJson = gson.fromJson<List<AirportJson>>(jsonString, itemType)

           airportRepository?.apply {
               saveAll(
                       airportJson.map {it ->
                           Airport.builder()
                                   .id(it.iata_code)
                                   .city(it.city)
                                   .country(it.country)
                                   .name(it.name)
                                   .build()
                       }
               )
           }
           return airportJson[0].iata_code
        }
    }

}