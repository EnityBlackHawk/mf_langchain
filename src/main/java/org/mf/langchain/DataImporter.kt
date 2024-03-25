package org.mf.langchain

import com.google.gson.Gson
import org.mf.langchain.DTO.AirportJson
import org.mf.langchain.repositories.AirportRepository
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.reflect.TypeToken;
import org.mf.langchain.model.Airport

class DataImporter {

    companion object{
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