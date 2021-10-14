package com.example.jokeapp

import android.util.Log
import kotlin.random.Random

data class JokeBook (var currentJoke: String = ""){


    val jokes = listOf(
        "My wife said I should do lunges to stay in shape. That would be a big step forward.",
        "I thought the dryer was shrinking my clothes. Turns out it was the refrigerator all along",
        "I only know 25 letters of the alphabet. I don't know y.",
        "I asked my dog what's two minus two. He said nothing.",
        "This graveyard looks overcrowded. People must be dying to get in.",
        "I have a joke about chemistry, but I don't think it will get a reaction.",
        "I used to be addicted to soap, but I'm clean now."
    )

    fun getRandomJoke() : String {
        var randomListNumber = Random.nextInt(jokes.size)
        return jokes[randomListNumber];
    }

    fun changeCurrentJoke() {
        var randomListNumber = Random.nextInt(jokes.size)
        if(currentJoke == jokes[randomListNumber]) randomListNumber = randomListNumber.plus(1).mod(jokes.size)
        //use mod to stay in the correct range
        currentJoke = jokes[randomListNumber]
    }
}