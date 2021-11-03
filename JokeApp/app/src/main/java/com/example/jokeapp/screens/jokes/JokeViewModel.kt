package com.example.jokeapp.screens.jokes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import kotlin.random.Random

class JokeViewModel: ViewModel() {

    var happyJokes = 0
    var badJokes = 0

    private val jokes = listOf(
        "My wife said I should do lunges to stay in shape. That would be a big step forward.",
        "I thought the dryer was shrinking my clothes. Turns out it was the refrigerator all along",
        "I only know 25 letters of the alphabet. I don't know y.",
        "I asked my dog what's two minus two. He said nothing.",
        "This graveyard looks overcrowded. People must be dying to get in.",
        "I have a joke about chemistry, but I don't think it will get a reaction.",
        "I used to be addicted to soap, but I'm clean now."
    )

    private val _currentJoke = MutableLiveData<String>()
    val currentJoke: LiveData<String>
        get() = _currentJoke

    private val _shouldEvaluate = MutableLiveData<Boolean>()
    val shouldEvaluate : LiveData<Boolean>
        get() = _shouldEvaluate

    private val _showSmileyEvent = MutableLiveData<Boolean>()
    val showSmileyEvent : LiveData<Boolean>
        get() = _showSmileyEvent

    init {
        Timber.i("init is called")
        changeCurrentJoke()
    }

    fun changeCurrentJoke() {
        //Check for evaluation:
        if (happyJokes + badJokes == 3){
            _shouldEvaluate.value = true
            startOver()
            return
        }

        var randomListNumber = Random.nextInt(jokes.size)
        if(_currentJoke.value == jokes[randomListNumber]) randomListNumber = randomListNumber.plus(1).mod(jokes.size)
        //use mod to stay in the correct range
        _currentJoke.value = jokes[randomListNumber]
    }

    fun evaluationComplete(){
        _shouldEvaluate.value = false
    }

    fun showImageComplete(){
        _showSmileyEvent.value = false
    }

    fun isHappy() = happyJokes > 2
    //fun shouldEvaluate() = happyJokes+badJokes > 2

    var totalJokesTold = 0

    fun startOver() {
        happyJokes = 0
        badJokes = 0

    }

    fun goodJoke() {
        happyJokes++
        changeCurrentJoke()
        _showSmileyEvent.value = true
    }

    fun badJoke() {
        badJokes++
        changeCurrentJoke()
    }



}