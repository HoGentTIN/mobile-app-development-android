package com.example.taskapp.fake

import com.example.taskapp.data.TasksRepository
import com.example.taskapp.model.Task
import com.example.taskapp.network.asDomainObjects

class FakeApiTasksRepository: TasksRepository {
    override suspend fun getTasks(): List<Task> {
        return FakeDataSource.tasks.asDomainObjects()
    }
}