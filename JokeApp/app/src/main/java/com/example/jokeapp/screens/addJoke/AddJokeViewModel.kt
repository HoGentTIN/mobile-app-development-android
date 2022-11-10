package com.example.jokeapp.screens.addJoke

import android.app.Application
import androidx.lifecycle.*
import com.example.jokeapp.database.jokes.Joke
import com.example.jokeapp.database.jokes.JokeDatabaseDao
import kotlinx.coroutines.launch

class AddJokeViewModel(val database: JokeDatabaseDao, application: Application): AndroidViewModel(application) {

    private val _saveEvent = MutableLiveData<Boolean>()
    val saveEvent: LiveData<Boolean>
        get() = _saveEvent

    init {
        _saveEvent.value = false
    }

    fun saveJokeClick(){
        _saveEvent.value = true
    }

    fun saveEventDone(){
        _saveEvent.value = false
    }

    fun saveJoke(newJoke : String){
        viewModelScope.launch{
            val joke = Joke()
            joke.punchline = newJoke
            saveJokeToDatabase(joke)
        }
    }

    //suspend methods
    suspend fun saveJokeToDatabase(newJoke: Joke){
        database.insert(newJoke)
    }
}