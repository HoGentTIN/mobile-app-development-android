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

enum class JokeApiStatus { LOADING, ERROR, DONE }

class FromAPIViewModel: ViewModel() {

    private var _apiResponse = MutableLiveData<ApiJoke>()
    val apiResponse: LiveData<ApiJoke>
        get() = _apiResponse

    private val _status = MutableLiveData<JokeApiStatus>()
    val status: LiveData<JokeApiStatus>
        get() = _status


    init {

        Timber.i("getting jokes")
        viewModelScope.launch {
            getJokesFromAPI()
        }

    }

    private suspend fun getJokesFromAPI() {
        _status.value = JokeApiStatus.LOADING
        var getJokesDeferred = JokeApi.retrofitService.getJokes()

        try {
            var res = getJokesDeferred.await()
            _status.value = JokeApiStatus.DONE
            _apiResponse.value = res
        } catch (e: Exception){
            _status.value = JokeApiStatus.ERROR
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}