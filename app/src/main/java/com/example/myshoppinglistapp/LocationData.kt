package com.example.myshoppinglistapp

data class LocationData(
    val latitude:Double,
    val longitude:Double
)

data class Geocodingresponse(
    val results:List<Geocodingresult>,
    val status: String
)

data class Geocodingresult(
    val formatted_address:String
)
