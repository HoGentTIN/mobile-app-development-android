package com.example.taskapp.data

import com.example.taskapp.data.database.TaskDao
import com.example.taskapp.data.database.asDbTask
import com.example.taskapp.data.database.asDomainTask
import com.example.taskapp.data.database.asDomainTasks
import com.example.taskapp.model.Task
import com.example.taskapp.network.TaskApiService
import com.example.taskapp.network.asDomainObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TasksRepository {
    //all items from datasource
    fun getTasks(): Flow<List<Task>>

    //one specific item
    fun getTask(id: Int): Flow<Task?>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)


}

class OfflineTasksRepository(private val taskDao: TaskDao): TasksRepository{
    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getAllItems().map {
            it.asDomainTasks()
        }
    }

    override fun getTask(id: Int): Flow<Task?> {
        return taskDao.getItem(id).map {
            it.asDomainTask()
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insert(task.asDbTask())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task.asDbTask())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task.asDbTask())
    }
}

/*
class ApiTasksRepository(
    private val taskApiService: TaskApiService
): TasksRepository{
    override fun getTasks(): List<Task> {
        return taskApiService.getTasks().asDomainObjects()
    }
}*/