package com.example.jokeapp.screens.jokes

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import com.example.jokeapp.database.jokes.Joke
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.database.jokes.JokeDatabaseDao
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random

class JokeViewModel(val database: JokeDatabaseDao, application: Application): AndroidViewModel(application) {

    var happyJokes = 0
    var badJokes = 0

    /*private val jokes = listOf(
        "My wife said I should do lunges to stay in shape. That would be a big step forward.",
        "I thought the dryer was shrinking my clothes. Turns out it was the refrigerator all along",
        "I only know 25 letters of the alphabet. I don't know y.",
        "I asked my dog what's two minus two. He said nothing.",
        "This graveyard looks overcrowded. People must be dying to get in.",
        "I have a joke about chemistry, but I don't think it will get a reaction.",
        "I used to be addicted to soap, but I'm clean now."
    )*/

    //Jokes will be a livedata field because the db returns it as livedata
    private lateinit var jokes: List<Joke>
    //numberOfJokes is no livedata yet --> wrap it in livedata here.
    private val numberOfJokes = MutableLiveData<Int>()

    val numberOfJokesString = Transformations.map(numberOfJokes){
        number -> number.toString()
    }

    val buttonVisible = Transformations.map(numberOfJokes){
        it > 0
    }

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
        initializeLiveData()

        viewModelScope.launch{
            jokes = getAllJokes()
            changeCurrentJoke()
        }


    }

    private fun initializeLiveData(){
        viewModelScope.launch{
            numberOfJokes.value = getNumberOfJokesFromDatabase()
        }
    }

    fun changeCurrentJoke() {
        //Check for evaluation:
        if (happyJokes + badJokes == 3){
            _shouldEvaluate.value = true
            startOver()
            return
        }
        viewModelScope.launch {
            getAllJokes()
        }
        _currentJoke.value = "Create some jokes first"
        if(numberOfJokes.value == null) return

        //don't change the joke if there are no jokes
        if(numberOfJokes.value!! == 0) return

        var randomListNumber = Random.nextInt(numberOfJokes.value!!)

        //use the livedata joke list to get a random joke
        if(_currentJoke.value == jokes.get(randomListNumber).punchline) randomListNumber = randomListNumber.plus(1).mod(numberOfJokes.value!!)
        //use mod to stay in the correct range
        _currentJoke.value = jokes.get(randomListNumber).punchline

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



    //Suspend functions
    private suspend fun getNumberOfJokesFromDatabase(): Int{
        return database.numberOfJokes()
    }

    private suspend fun getAllJokes(): List<Joke>{
        return database.getAllJokes()
    }
}