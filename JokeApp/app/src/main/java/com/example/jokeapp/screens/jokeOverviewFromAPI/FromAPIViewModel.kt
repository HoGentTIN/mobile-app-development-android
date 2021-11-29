package com.example.jokeapp.screens.jokeOverviewFromAPI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jokeapp.database.jokes.Joke
import com.example.jokeapp.network.ApiJoke
import com.example.jokeapp.network.JokeApi
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
        getJokesFromAPI()
    }

    private fun getJokesFromAPI() {
        JokeApi.retrofitService.getJokes().enqueue(object: Callback<ApiJoke> {
            override fun onResponse(call: Call<ApiJoke>, response: Response<ApiJoke>) {
                _apiResponse.value = response.body()?.toString()
                //Timber.i(response.body())

            }

            override fun onFailure(call: Call<ApiJoke>, t: Throwable) {
                _apiResponse.value = "Failure: " + t.message
                Timber.i(t.message)
            }

        })
    }

}