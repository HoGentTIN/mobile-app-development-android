package com.example.taskapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun TaskOverview(
    addingVisible: Boolean,
    onVisibilityChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    taskOverviewViewModel: TaskOverviewViewModel = viewModel(),
) {
    val taskOverviewState by taskOverviewViewModel.uiState.collectAsState()

    Box(modifier = modifier) {
        val lazyListState = rememberLazyListState()
        LazyColumn(state = lazyListState) {
            items(taskOverviewState.currentTaskList) {
                TaskItem(name = it.name, description = it.description)
            }
        }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(taskOverviewState.currentTaskList.size) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(taskOverviewState.currentTaskList.size - 1)
            }
        }

        if (addingVisible) {
            CreateTask(
                taskName = taskOverviewState.newTaskName,
                taskDescription = taskOverviewState.newTaskDescription,
                onTaskNameChanged = { taskOverviewViewModel.setNewTaskName(it) },
                onTaskDescriptionChanged = { taskOverviewViewModel.setNewTaskDescription(it) },
                onTaskSaved = {
                    taskOverviewViewModel.addTask()
                    onVisibilityChanged(false)
                },
                { onVisibilityChanged(false) },
            )
        }
    }
}
