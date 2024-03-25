package org.mf.langchain.DTO

data class AirlineJson(
        val id : Int,
        val name : String,
        val iata : String,
        val icao : String,
        val callsign : String,
        val country : String,
        val active : String
)
