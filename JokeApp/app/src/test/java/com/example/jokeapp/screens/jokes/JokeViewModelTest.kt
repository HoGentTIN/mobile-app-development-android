package com.example.jokeapp.screens.jokes

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JokeViewModelTest{
    @Test
    fun simple_jokeModel_test(){
        val jokeViewModel = JokeViewModel()
        jokeViewModel.startOver()

        assertThat(jokeViewModel.happyJokes, `is`(0))
    }

    @Test
    fun failing_jokeModel_test(){
        val jokeViewModel = JokeViewModel()
        jokeViewModel.startOver()

        assertThat(jokeViewModel.happyJokes, `is`(5))
    }

}