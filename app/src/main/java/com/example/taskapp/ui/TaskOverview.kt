package com.example.taskapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskapp.model.Task

@Composable
fun TaskOverview(
    addingVisible: Boolean,
    onVisibilityChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    taskOverviewViewModel: TaskOverviewViewModel = viewModel(),
) {
    val taskOverviewState by taskOverviewViewModel.uiState.collectAsState()

    Box(modifier = modifier) {
        LazyColumn() {
            items(taskOverviewState.currentTaskList) {
                TaskItem(name = it.name, description = it.description)
            }
        }
        // on top of the list: the input fields
        var newTaskName by remember { mutableStateOf("") }
        var newTaskDescription by remember { mutableStateOf("") }

        if (addingVisible) {
            CreateTask(
                taskName = newTaskName,
                taskDescription = newTaskDescription,
                onTaskNameChanged = { name -> newTaskName = name },
                onTaskDescriptionChanged = { description -> newTaskDescription = description },
                onTaskSaved = { // this might be useful later
                    taskOverviewViewModel.addTask(Task(newTaskName))
                    onVisibilityChanged(false)
                    newTaskName = ""
                    newTaskDescription = ""
                },
            )
        }
    }
}
