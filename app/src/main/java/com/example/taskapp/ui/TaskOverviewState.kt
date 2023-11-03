package com.example.taskapp.ui

import com.example.taskapp.model.Task

// the data class just holds the (immutable) values of the state
data class TaskOverviewState(
    val currentTaskList: List<Task>,
    val newTaskName: String = "",
    val newTaskDescription: String = "",
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)

//the sealed interface has only three possible values
sealed interface TaskApiState{
    data class Success(val tasks: List<Task>) : TaskApiState
    object Error: TaskApiState
    object Loading : TaskApiState
}
