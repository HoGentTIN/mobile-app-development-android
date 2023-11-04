package com.example.taskapp

import com.example.taskapp.fake.FakeApiTasksRepository
import com.example.taskapp.ui.TaskOverviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TaskOverviewViewModelTest {


    private val someTaskName = "some task name"

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun settingNameChangesState() {
        val viewModel = TaskOverviewViewModel(
            tasksRepository = FakeApiTasksRepository()
        )
        viewModel.setNewTaskName(someTaskName)
        Assert.assertEquals(viewModel.uiState.value.newTaskName, someTaskName)

    }

}

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher(){
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
