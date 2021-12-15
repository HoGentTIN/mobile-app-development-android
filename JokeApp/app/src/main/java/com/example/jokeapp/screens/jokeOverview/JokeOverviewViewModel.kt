package com.example.jokeapp.screens.jokeOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jokeapp.database.jokes.DatabaseJoke
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.database.jokes.JokeDatabaseDao
import com.example.jokeapp.repository.JokeRepository

class JokeOverviewViewModel(val database: JokeDatabaseDao, app: Application ): AndroidViewModel(app) {

    private var currentFilter: String? = null

    val db = JokeDatabase.getInstance(app.applicationContext)
    val repository = JokeRepository(db)

    //var jokes = database.getAllJokesLive()
    val jokes = repository.jokes

    fun filterChip(changedFilter: String, isChecked: Boolean) {
        //set currentFilter
        if (isChecked) {
            currentFilter = changedFilter
        } else if (currentFilter == changedFilter) {
            currentFilter = null
        }

        repository.addFilter(currentFilter)
    }

}