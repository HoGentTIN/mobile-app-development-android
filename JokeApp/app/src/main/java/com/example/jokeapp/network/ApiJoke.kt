package com.example.jokeapp.network

import com.example.jokeapp.database.jokes.Joke
import com.squareup.moshi.Json

data class ApiJoke (

    val body: List<Joke>
)