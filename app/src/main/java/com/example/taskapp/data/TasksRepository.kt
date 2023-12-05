package com.example.taskapp.data

import android.util.Log
import com.example.taskapp.data.database.TaskDao
import com.example.taskapp.data.database.asDbTask
import com.example.taskapp.data.database.asDomainTask
import com.example.taskapp.data.database.asDomainTasks
import com.example.taskapp.model.Task
import com.example.taskapp.network.TaskApiService
import com.example.taskapp.network.asDomainObjects
import com.example.taskapp.network.getTasksAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

interface TasksRepository {
    // all items from datasource
    fun getTasks(): Flow<List<Task>>

    // one specific item
    fun getTask(id: String): Flow<Task?>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun refresh()
}

class CachingTasksRepository(private val taskDao: TaskDao, private val taskApiService: TaskApiService) : TasksRepository {

    // this repo contains logic to refresh the tasks (remote)
    // sometimes that logic is written in a 'usecase'
    override fun getTasks(): Flow<List<Task>> {
        // checkes the array of items comming in
        // when empty --> tries to fetch from API
        // clear the DB if inspector is broken...
        /*runBlocking { taskDao.getAllItems().collect{
            for(t: dbTask in it)
                taskDao.delete(t)
        } }*/
        return taskDao.getAllItems().map {
            it.asDomainTasks()
        }.onEach {
            // todo: check when refresh is called (why duplicates??)
            if (it.isEmpty()) {
                refresh()
            }
        }
    }

    override fun getTask(name: String): Flow<Task?> {
        return taskDao.getItem(name).map {
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

    override suspend fun refresh() {
        try {
            taskApiService.getTasksAsFlow().asDomainObjects().collect {
                    value ->
                for (task in value) {
                    Log.i("TEST", "refresh: $value")
                    insertTask(task)
                }
            }
        } catch (e: SocketTimeoutException) {
            // log something
        }
    }
}

/*
class ApiTasksRepository(
    private val taskApiService: TaskApiService
): TasksRepository{
    override fun getTasks(): List<Task>> {
        return taskApiService.getTasks().asDomainObjects()
    }

    override fun getTask(id: Int): Flow<Task?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }
}*/
