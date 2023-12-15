package com.example.taskapp.data

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.taskapp.data.database.TaskDao
import com.example.taskapp.data.database.asDbTask
import com.example.taskapp.data.database.asDomainTask
import com.example.taskapp.data.database.asDomainTasks
import com.example.taskapp.model.Task
import com.example.taskapp.network.TaskApiService
import com.example.taskapp.network.asDomainObjects
import com.example.taskapp.network.getTasksAsFlow
import com.example.taskapp.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException
import java.util.UUID

interface TasksRepository {
    // all items from datasource
    fun getTasks(): Flow<List<Task>>

    // one specific item
    fun getTask(id: String): Flow<Task?>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun refresh()

    var wifiWorkInfo: Flow<WorkInfo>
}

class CachingTasksRepository(private val taskDao: TaskDao, private val taskApiService: TaskApiService, context: Context) : TasksRepository {

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

    private var workID = UUID(1,2)
    //the manager is private to the repository
    private val workManager = WorkManager.getInstance(context)
    //the info function is public
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workID)

    override suspend fun refresh() {
        //refresh is used to schedule the workrequest

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val requestBuilder = OneTimeWorkRequestBuilder<WifiNotificationWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
        workID = request.id
        wifiWorkInfo = workManager.getWorkInfoByIdFlow(request.id)



        //note the actual api request still uses coroutines
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

