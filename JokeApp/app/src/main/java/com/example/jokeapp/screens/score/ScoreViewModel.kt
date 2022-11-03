package com.example.jokeapp.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(happyJokesCount: Int, sadJokesCount: Int) : ViewModel(){

    private val _happyJokes = MutableLiveData<Int>()
    val happyJokes: LiveData<Int>
        get() = _happyJokes

    private val _sadJokes = MutableLiveData<Int>()
    val sadJokes: LiveData<Int>
        get() = _sadJokes

    init {
        _happyJokes.value = happyJokesCount
        _sadJokes.value = sadJokesCount
        //Timber.i("happyJokeCount: ${happyJokesCount}")
        //Timber.i("sadJokeCount: ${sadJokesCount}")
    }

    fun isHappy() = happyJokes.value == 3

}