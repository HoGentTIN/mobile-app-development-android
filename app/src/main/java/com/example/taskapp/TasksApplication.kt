package com.example.taskapp

import android.app.Application
import com.example.taskapp.data.AppContainer
import com.example.taskapp.data.DefaultAppContainer



class TasksApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}