package com.example.jokeapp

class Comedian (var happyJokes:Int = 0, var badJokes: Int = 0) {
    /**
     * A Comedian tells joke using a Jokebook
     * The comedian can get good and bad points. Every three jokes there is an evaluation
     */
    fun isHappy() = happyJokes > 2
    fun shouldEvaluate() = happyJokes+badJokes > 2

    var totalJokesTold = 0

    fun startOver() {
        happyJokes = 0
        badJokes = 0

    }

    fun goodJoke() {
        happyJokes++
    }

    fun badJoke() {
        badJokes++
    }


}