package com.example.jokeapp.screens.jokeOverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jokeapp.database.jokes.JokeDatabaseDao
import com.example.jokeapp.screens.jokes.JokeViewModel

class JokeOverviewViewModelFactory(private val dataSource: JokeDatabaseDao, private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(JokeOverviewViewModel::class.java)) {
            return JokeOverviewViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
