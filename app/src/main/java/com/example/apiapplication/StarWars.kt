package com.example.apiapplication

data class StarWars(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<ResultSW>
)