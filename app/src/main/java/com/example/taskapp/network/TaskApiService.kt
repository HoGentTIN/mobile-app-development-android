package com.example.taskapp.network

import com.example.taskapp.model.Task
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET



//create the actual function implementations (expensive!)
//no longer needed --> moved to the AppContainer
//object TaskApi{
//
//}


//define what the API looks like
interface TaskApiService {
    //suspend is added to force the user to call this in a coroutine scope
    @GET("tasks")
    suspend fun getTasks(): List<ApiTask>
}


