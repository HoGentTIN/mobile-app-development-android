package com.example.taskapp

import com.example.taskapp.ui.TaskOverviewViewModel
import org.junit.Assert
import org.junit.Test

class TaskOverviewViewModelTest {
    private val viewModel = TaskOverviewViewModel()
    private val someTaskName = "some task name"

    @Test
    fun settingNameChangesState() {
        viewModel.setNewTaskName(someTaskName)

        Assert.assertEquals(viewModel.uiState.value.newTaskName, someTaskName)
    }
}
