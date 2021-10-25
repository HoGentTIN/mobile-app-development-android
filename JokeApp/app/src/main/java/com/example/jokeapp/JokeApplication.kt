package com.example.jokeapp

import android.app.Application
import timber.log.Timber

class JokeApplication: Application() {
    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}