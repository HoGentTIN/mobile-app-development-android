package com.example.jokeapp.domain


/*
* Model class: this is how the joke is known in the entire app
* */
data class Joke(
         var jokeId: Long = 0L,
         var jokeSetup: String = "",
         var jokeType: String = "",
         var punchline: String = "",){

    val smileyUri = "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/285/grinning-face-with-big-eyes_1f603.png"

}