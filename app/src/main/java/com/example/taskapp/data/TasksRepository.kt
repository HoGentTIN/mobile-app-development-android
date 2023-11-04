package com.example.taskapp.data

import com.example.taskapp.model.Task
import com.example.taskapp.network.TaskApiService
import com.example.taskapp.network.asDomainObjects

interface TasksRepository {
    suspend fun getTasks(): List<Task>
}

class ApiTasksRepository(
    private val taskApiService: TaskApiService
): TasksRepository{
    override suspend fun getTasks(): List<Task> {
        return taskApiService.getTasks().asDomainObjects()
    }
}