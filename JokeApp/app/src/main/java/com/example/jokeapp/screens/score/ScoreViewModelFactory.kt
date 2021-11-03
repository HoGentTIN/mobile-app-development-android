package com.example.jokeapp.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(private val happyJokes: Int, private val sadJokes: Int): ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(happyJokes, sadJokes) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}