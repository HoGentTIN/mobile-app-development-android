package com.example.taskapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.taskapp.data.database.TaskDao
import com.example.taskapp.data.database.TaskDb
import com.example.taskapp.data.database.asDbTask
import com.example.taskapp.data.database.asDomainTask
import com.example.taskapp.model.Task
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class TaskDaoTest {
    private lateinit var taskDao: TaskDao
    private lateinit var taskDb: TaskDb

    private var task1 = Task("first", "some text here")
    private var task2 = Task("second", "some other thing here")


    //unility functions
    private suspend fun addOneTaskToDb() {
        taskDao.insert(task1.asDbTask())
    }

    private suspend fun addTwoTasksToDb() {
        taskDao.insert(task1.asDbTask())
        taskDao.insert(task2.asDbTask())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        taskDb = Room.inMemoryDatabaseBuilder(context, TaskDb::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        taskDao = taskDb.taskDao()
    }
    @After
    @Throws(IOException::class)
    fun closeDb() {
        taskDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInert_insertTaskIntoDB() = runBlocking{
        addOneTaskToDb()
        val allItems = taskDao.getAllItems().first()
        assertEquals(allItems[0].asDomainTask(), task1)

    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllTasks_returnsAllTasksFromDB() = runBlocking {
        addTwoTasksToDb()
        val allItems = taskDao.getAllItems().first()
        assertEquals(allItems[0].asDomainTask(), task1)
        assertEquals(allItems[1].asDomainTask(), task2)
    }

}