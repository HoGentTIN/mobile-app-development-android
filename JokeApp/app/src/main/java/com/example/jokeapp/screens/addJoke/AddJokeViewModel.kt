package com.example.jokeapp.screens.addJoke

import android.app.Application
import androidx.lifecycle.*
import com.example.jokeapp.database.jokes.DatabaseJoke
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.database.jokes.JokeDatabaseDao
import com.example.jokeapp.domain.Joke
import com.example.jokeapp.repository.JokeRepository
import kotlinx.coroutines.launch

class AddJokeViewModel(val database: JokeDatabaseDao, application: Application): AndroidViewModel(application) {

    private val _saveEvent = MutableLiveData<Boolean>()
    val saveEvent: LiveData<Boolean>
        get() = _saveEvent
    private val db = JokeDatabase.getInstance(application.applicationContext)
    private val repository = JokeRepository(db)

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
            //saveJokeToDatabase(joke)
            saveJokeWithRepository(joke)
        }
    }

    //suspend methods
    suspend fun saveJokeToDatabase(newJoke: DatabaseJoke){
        database.insert(newJoke)
    }

    suspend fun saveJokeWithRepository(newJoke: Joke){
        repository.createJoke(newJoke)
    }



}