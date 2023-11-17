package com.example.taskapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
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
    taskOverviewViewModel: TaskOverviewViewModel = viewModel(factory = TaskOverviewViewModel.Factory),
) {
    val taskOverviewState by taskOverviewViewModel.uiState.collectAsState()

    //use the ApiState
    val taskApiState = taskOverviewViewModel.taskApiState

    Box(modifier = modifier) {
        when(taskApiState){
            is TaskApiState.Loading -> Text("Loading...")
            is TaskApiState.Error -> Text("Couldn't load...")
            is TaskApiState.Success -> TaskListComponent(taskOverviewState = taskOverviewState)
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


@Composable
fun TaskListComponent(modifier: Modifier = Modifier, taskOverviewState: TaskOverviewState) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(taskOverviewState.currentTaskList) {
            TaskItem(name = it.name, description = it.description)
        }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(taskOverviewState.scrollActionIdx) {
        if (taskOverviewState.scrollActionIdx != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(taskOverviewState.scrollToItemIndex)
            }
        }
    }
    
}
