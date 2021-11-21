package com.example.jokeapp.screens.jokeOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.jokeapp.database.jokes.JokeDatabaseDao

class JokeOverviewViewModel(val database: JokeDatabaseDao, app: Application  ): AndroidViewModel(app) {
    val jokes = database.getAllJokesLive()

}