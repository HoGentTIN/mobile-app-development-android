package com.example.jokeapp.screens.jokeOverviewFromAPI

import android.app.Application
import androidx.lifecycle.*
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.domain.Joke
import com.example.jokeapp.network.ApiJoke
import com.example.jokeapp.network.JokeApi
import com.example.jokeapp.network.asDatabaseModel
import com.example.jokeapp.network.asDomainModel
import com.example.jokeapp.repository.JokeRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

enum class JokeApiStatus { LOADING, ERROR, DONE }

class FromAPIViewModel(application: Application): AndroidViewModel(application) {

    private val _status = MutableLiveData<JokeApiStatus>()
    val status: LiveData<JokeApiStatus>
        get() = _status


    private val database = JokeDatabase.getInstance(application.applicationContext)
    private val jokeRepository = JokeRepository(database)

    val jokes = jokeRepository.jokes

    init {

        Timber.i("getting jokes")
        viewModelScope.launch {
            _status.value = JokeApiStatus.LOADING
           jokeRepository.refreshJokes()
            _status.value = JokeApiStatus.DONE
        }

    }

    /*
    * No longer needed: the repository will take care of this
    * */
    /*private suspend fun getJokesFromAPI() {
        _status.value = JokeApiStatus.LOADING
        var getJokesDeferred = JokeApi.retrofitService.getJokes()

        try {
            var res = getJokesDeferred.await()
            _status.value = JokeApiStatus.DONE
            _apiResponse.value = res.asDomainModel().get(0)
        } catch (e: Exception){
            _status.value = JokeApiStatus.ERROR
        }
    }*/

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }




    /**
     * Factory for constructing FromAPIViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FromAPIViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FromAPIViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

