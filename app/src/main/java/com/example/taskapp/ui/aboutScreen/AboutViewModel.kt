package com.example.taskapp.ui.aboutScreen

import android.util.Log
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {
    init {
        Log.i("vm inspection", "AboutViewModel init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("vm inspection", "AboutViewModel cleared")
    }
}
