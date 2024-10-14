package com.example.myshoppinglistapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val Base_URl ="https://maps.googleapis.com/"

    fun create():GeoCodingApi
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(Base_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        return retrofit.create(GeoCodingApi::class.java)
    }
}