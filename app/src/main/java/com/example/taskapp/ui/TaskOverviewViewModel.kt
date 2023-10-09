package com.example.taskapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.taskapp.data.TaskSampler
import com.example.taskapp.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskOverviewViewModel: ViewModel() {
    fun addTask(task: Task) {
        taskList.add(task)
        _uiState.update {
            currentState -> currentState.copy(currentTaskList = taskList.toMutableStateList())
        }
    }

    private var taskList: MutableList<Task> = mutableListOf()

    //use StateFlow (Flow: emits current state + any updates)
    private val _uiState = MutableStateFlow(TaskOverviewState(taskList.toMutableStateList()))
    val uiState : StateFlow<TaskOverviewState> = _uiState.asStateFlow()


    init {
        taskList = TaskSampler.getAll()
        _uiState.value = TaskOverviewState(taskList.toMutableStateList())
    }
}

//the data class just holds the (immutable) values of the state
data class TaskOverviewState(val currentTaskList: SnapshotStateList<Task> ){
    //List<MutableState<Task>>
}