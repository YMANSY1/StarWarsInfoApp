package com.example.apiapplication

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.google.gson.*
import java.lang.reflect.Type


object RetrofitInstance {
    val api: StarWarsAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StarWarsAPI::class.java)
    }

}