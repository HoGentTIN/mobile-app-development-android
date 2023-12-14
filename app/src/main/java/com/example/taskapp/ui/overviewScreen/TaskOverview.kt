package com.example.taskapp.ui.overviewScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskapp.model.Task
import com.example.taskapp.ui.components.CreateTask
import com.example.taskapp.ui.components.TaskItem
import kotlinx.coroutines.launch

@Composable
fun TaskOverview(
    modifier: Modifier = Modifier,
    taskOverviewViewModel: TaskOverviewViewModel = viewModel(factory = TaskOverviewViewModel.Factory),
    isAddingVisisble: Boolean = false,
    makeInvisible: () -> Unit = {},
) {
    Log.i("vm inspection", "TaskOverview composition")
    val taskOverviewState by taskOverviewViewModel.uiState.collectAsState()
    val taskListState by taskOverviewViewModel.uiListState.collectAsState()

    // use the ApiState
    val taskApiState = taskOverviewViewModel.taskApiState
    
    //use the workerstate
    val workerState by taskOverviewViewModel.wifiWorkerState.collectAsState()
    Column {
        when(workerState.workerInfo?.state){
            null -> Text("state unknown")
            else -> Text(workerState.workerInfo?.state!!.name)
        }

        Box(modifier = modifier) {
            when (taskApiState) {
                is TaskApiState.Loading -> Text("Loading...")
                is TaskApiState.Error -> Text("Couldn't load...")
                is TaskApiState.Success -> TaskListComponent(taskOverviewState = taskOverviewState, taskListState = taskListState)
            }
    
            if (isAddingVisisble) {
                CreateTask(
                    taskName = taskOverviewState.newTaskName,
                    taskDescription = taskOverviewState.newTaskDescription,
                    onTaskNameChanged = { taskOverviewViewModel.setNewTaskName(it) },
                    onTaskDescriptionChanged = { taskOverviewViewModel.setNewTaskDescription(it) },
                    onTaskSaved = {
                        taskOverviewViewModel.addTask()
                        makeInvisible()
                    },
                    onDismissRequest = { makeInvisible() },
                )
            }
        }
    }
}

@Composable
fun TaskListComponent(modifier: Modifier = Modifier, taskOverviewState: TaskOverviewState, taskListState: TaskListState) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(taskListState.taskList) {
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

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun TaskListComponentPreview() {
    TaskListComponent(taskOverviewState = TaskOverviewState(), taskListState = TaskListState(listOf(Task("previewtask", "description"))))
}
