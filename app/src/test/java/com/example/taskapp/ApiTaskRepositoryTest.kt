package com.example.taskapp

import com.example.taskapp.data.ApiTasksRepository
import com.example.taskapp.fake.FakeDataSource
import com.example.taskapp.fake.FakeTasksApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiTaskRepositoryTest {
    @Test
    fun apiTaskRepository_getTasks_verifyTasksList() =
        runTest{
        val repository = ApiTasksRepository(FakeTasksApiService())
        assertEquals(FakeDataSource.tasks, repository.getTasks())
    }
}