package com.example.myshoppinglistapp

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GeoCodingApi {
    @GET("maps/api/geocode/json")
    suspend fun getAddressFromCoordinates(

        @Header("X-Android-Package") androidPackage: String,
        @Header("X-Android-Cert") sha1: String,

        @Query("latlng") latlng:String,
        @Query("key") apikey:String
    ):Geocodingresponse
}