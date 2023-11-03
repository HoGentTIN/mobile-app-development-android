package com.example.taskapp.network

import com.example.taskapp.model.Task
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:3000"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
    .build()

//create the actual function implementations (expensive!)
object TaskApi{
    val retrofitService : TaskApiService by lazy {
        retrofit.create(TaskApiService::class.java)
    }
}


//define what the API looks like
interface TaskApiService {
    //suspend is added to force the user to call this in a coroutine scope
    @GET("tasks")
    suspend fun getTasks(): List<ApiTask>
}


