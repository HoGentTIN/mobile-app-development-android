package com.example.taskapp.ui

import androidx.lifecycle.ViewModel
import com.example.taskapp.data.TaskSampler
import com.example.taskapp.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskOverviewViewModel : ViewModel() {
    // use StateFlow (Flow: emits current state + any updates)
    private val _uiState = MutableStateFlow(TaskOverviewState(TaskSampler.getAll()))
    val uiState: StateFlow<TaskOverviewState> = _uiState.asStateFlow()

    fun addTask(task: Task) {
        _uiState.update {
                currentState ->
            currentState.copy(currentTaskList = currentState.currentTaskList + task)
        }
    }
}
