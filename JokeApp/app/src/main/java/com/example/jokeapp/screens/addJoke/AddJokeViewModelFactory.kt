package com.example.jokeapp.screens.addJoke

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jokeapp.database.jokes.JokeDatabaseDao

class AddJokeViewModelFactory (private val dataSource: JokeDatabaseDao, private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddJokeViewModel::class.java)) {
            return AddJokeViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}