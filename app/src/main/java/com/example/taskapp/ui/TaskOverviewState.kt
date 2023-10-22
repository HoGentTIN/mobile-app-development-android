package com.example.taskapp.ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.taskapp.model.Task

// the data class just holds the (immutable) values of the state
data class TaskOverviewState(
    val currentTaskList: SnapshotStateList<Task>,
)
