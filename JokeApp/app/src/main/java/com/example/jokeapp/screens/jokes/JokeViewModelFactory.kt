package com.example.jokeapp.screens.jokes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jokeapp.database.jokes.JokeDatabaseDao

class JokeViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(JokeViewModel::class.java)) {
            return JokeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
