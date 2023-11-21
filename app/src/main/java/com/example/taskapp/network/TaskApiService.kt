package com.example.taskapp.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

//helper function
fun TaskApiService.getTasksAsFlow(): Flow<List<ApiTask>> = flow {
        emit(getTasks())
    }


