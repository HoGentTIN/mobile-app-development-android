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

    fun addTask() {
        _uiState.update {
                currentState ->
            currentState.copy(
                currentTaskList = currentState.currentTaskList +
                    Task(currentState.newTaskName, currentState.newTaskDescription),
                // clean up previous values
                newTaskName = "",
                newTaskDescription = "",
                // whenever this changes, scrollToItemIndex should be scrolled into view
                scrollActionIdx = currentState.scrollActionIdx.plus(1),
                scrollToItemIndex = currentState.currentTaskList.size,
            )
        }
    }

    fun setNewTaskName(newTaskName: String) {
        _uiState.update {
            it.copy(newTaskName = newTaskName)
        }
    }

    fun setNewTaskDescription(newTaskDescription: String) {
        _uiState.update {
            it.copy(newTaskDescription = newTaskDescription)
        }
    }
}
