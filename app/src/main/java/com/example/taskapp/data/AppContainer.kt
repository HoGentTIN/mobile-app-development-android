package com.example.taskapp.data

import android.content.Context
import com.example.taskapp.data.database.TaskDb
import com.example.taskapp.network.TaskApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val tasksRepository: TasksRepository
}

//container that takes care of dependencies
class DefaultAppContainer(private val context: Context): AppContainer{

    private val baseUrl = "http://10.0.2.2:3000"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : TaskApiService by lazy {
        retrofit.create(TaskApiService::class.java)
    }

    /*
    override val tasksRepository: TasksRepository by lazy {
        ApiTasksRepository(retrofitService)
    }
    */
    override val tasksRepository: TasksRepository by lazy {
        CachingTasksRepository(TaskDb.getDatabase(context = context).taskDao(), retrofitService)
    }



}