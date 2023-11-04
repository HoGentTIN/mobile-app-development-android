package com.example.taskapp.data

import com.example.taskapp.model.Task
import com.example.taskapp.network.TaskApi
import com.example.taskapp.network.asDomainObjects

interface TasksRepository {
    suspend fun getTasks(): List<Task>
}

class ApiTasksRepository(): TasksRepository{
    override suspend fun getTasks(): List<Task> {
        return TaskApi.retrofitService.getTasks().asDomainObjects()
    }
}