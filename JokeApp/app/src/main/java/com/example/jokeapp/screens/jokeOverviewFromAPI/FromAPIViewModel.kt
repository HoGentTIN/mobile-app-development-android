package com.example.jokeapp.screens.jokeOverviewFromAPI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokeapp.database.jokes.Joke
import com.example.jokeapp.network.ApiJoke
import com.example.jokeapp.network.JokeApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FromAPIViewModel: ViewModel() {

    private var _apiResponse = MutableLiveData<String>()
    val apiResponse: LiveData<String>
        get() = _apiResponse

    init {
        _apiResponse.value = "add API response here"
        Timber.i("getting jokes")
        viewModelScope.launch {
            getJokesFromAPI()
        }

    }

    private suspend fun getJokesFromAPI() {
        var getJokesDeferred = JokeApi.retrofitService.getJokes()
        try {
            var res = getJokesDeferred.await()
            _apiResponse.value = res.body.get(0).punchline
        } catch (e: Exception){
            _apiResponse.value = "Failed to get jokes"
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}