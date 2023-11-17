package com.example.taskapp.fake

import com.example.taskapp.network.ApiTask
import com.example.taskapp.network.TaskApiService

class FakeTasksApiService: TaskApiService {
    override suspend fun getTasks(): List<ApiTask> {
        return FakeDataSource.tasks
    }
}