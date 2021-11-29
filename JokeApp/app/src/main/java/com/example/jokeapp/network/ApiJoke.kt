package com.example.jokeapp.network

import com.example.jokeapp.database.jokes.Joke
import com.squareup.moshi.Json

data class ApiJoke (

    val body: List<Joke>
){
    //Hardcoded image url to demo Glide
    val smileyUri = "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/285/grinning-face-with-big-eyes_1f603.png"

}