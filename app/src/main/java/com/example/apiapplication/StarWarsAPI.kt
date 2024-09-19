package com.example.apiapplication

import retrofit2.Response
import retrofit2.http.GET

interface StarWarsAPI {
    @GET("people/?format=json")
    suspend fun getStarWars(): Response<StarWars>
}