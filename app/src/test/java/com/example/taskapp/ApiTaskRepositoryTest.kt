package com.example.taskapp

import kotlinx.coroutines.test.runTest
import org.junit.Test

class ApiTaskRepositoryTest {
    @Test
    fun apiTaskRepository_getTasks_verifyTasksList() =
        runTest {
            //val repository = CachingTasksRepository(FakeTasksApiService())
            //assertEquals(FakeDataSource.tasks, repository.getTasks())
            //todo: update for roomdb
        }
}
