package com.example.jokeapp.screens.jokeOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jokeapp.database.jokes.DatabaseJoke
import com.example.jokeapp.database.jokes.JokeDatabaseDao

class JokeOverviewViewModel(val database: JokeDatabaseDao, app: Application ): AndroidViewModel(app) {

    private var currentFilter: String? = null


    var jokes = database.getAllJokesLive()

    val jokesChanged= MutableLiveData<Boolean>()
    init {
        jokesChanged.value = false
    }


    fun filterChip(changedFilter: String, isChecked: Boolean) {
        //set currentFilter
        if (isChecked) {
            currentFilter = changedFilter
        } else if (currentFilter == changedFilter) {
            currentFilter = null
        }

        when(currentFilter){
            null -> jokes = database.getAllJokesLive()
            "<10" -> jokes = database.getUnder10JokesLive()
            "10-20" -> jokes = database.getbetween1020JokesLive()
            ">20" -> jokes = database.getgreater20JokesLive()
        }

        jokesChanged.value = !jokesChanged.value!!

    }



}