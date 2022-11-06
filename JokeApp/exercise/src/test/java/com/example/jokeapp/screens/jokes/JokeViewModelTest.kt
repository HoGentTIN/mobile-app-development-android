package com.example.jokeapp.screens.jokes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jokeapp.getOrAwaitValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JokeViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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

    @Test
    fun test_liveData(){
        val jokeViewModel = JokeViewModel()

        jokeViewModel.changeCurrentJoke()

        val value = jokeViewModel.currentJoke.getOrAwaitValue()

        assertThat(value, not(nullValue()))
    }

}