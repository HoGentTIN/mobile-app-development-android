package com.example.jokeapp.screens.jokes

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import com.example.jokeapp.database.jokes.DatabaseJoke
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.database.jokes.JokeDatabaseDao
import com.example.jokeapp.repository.JokeRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random

class JokeViewModel(application: Application): AndroidViewModel(application) {

    var happyJokes = 0
    var badJokes = 0

    /*private val jokes = listOf(
        "My wife said I should do lunges to stay in shape. That would be a big step forward...."
    )*/


    private val _currentJoke = MutableLiveData<String>()
    val currentJoke: LiveData<String>
        get() = _currentJoke

    private val _shouldEvaluate = MutableLiveData<Boolean>()
    val shouldEvaluate : LiveData<Boolean>
        get() = _shouldEvaluate

    private val _showSmileyEvent = MutableLiveData<Boolean>()
    val showSmileyEvent : LiveData<Boolean>
        get() = _showSmileyEvent

    private val repository = JokeRepository(JokeDatabase.getInstance(application.applicationContext))

    val jokes = repository.jokes

    private val numberOfJokes = Transformations.map(jokes){
        it.size
    }
    val buttonVisible = Transformations.map(numberOfJokes){
        it > 0
    }

    val numberOfJokesString = Transformations.map(numberOfJokes){
            number -> number.toString()
    }


    fun changeCurrentJoke() {
        //Check for evaluation:
        if (happyJokes + badJokes == 3){
            _shouldEvaluate.value = true
            startOver()
            return
        }
        viewModelScope.launch {
            repository.refreshJokes() //fetches new jokes from the API
        }

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
    /*private suspend fun getNumberOfJokesFromDatabase(): Int{
        return database.numberOfJokes()
    }*/

    /*private suspend fun getAllJokes(): List<DatabaseJoke>{
        return database.getAllJokes()
    }*/
}