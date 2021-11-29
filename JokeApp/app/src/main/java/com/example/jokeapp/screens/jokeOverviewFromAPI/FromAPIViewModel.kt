package com.example.jokeapp.screens.jokeOverviewFromAPI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FromAPIViewModel: ViewModel() {

    private var _apiResponse = MutableLiveData<String>()
    val apiResponse: LiveData<String>
        get() = _apiResponse

    init {
        _apiResponse.value = "add API response here"
    }

}