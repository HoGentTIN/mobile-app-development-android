package com.example.taskapp.ui

import com.example.taskapp.model.Task

// the data class just holds the (immutable) values of the state
data class TaskOverviewState(
    val currentTaskList: List<Task>,
)
