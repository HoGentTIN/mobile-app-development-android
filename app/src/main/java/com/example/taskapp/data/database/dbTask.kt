package com.example.taskapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskapp.model.Task

@Entity(tableName = "tasks")
data class dbTask(
    @PrimaryKey
    val name: String = "",
    val description: String = "",
    val isDone: Boolean = false,

)

fun dbTask.asDomainTask(): Task {
    return Task(
        this.name,
        this.description,
    )
}

fun Task.asDbTask(): dbTask {
    return dbTask(
        name = this.name,
        description = this.description,
    )
}

fun List<dbTask>.asDomainTasks(): List<Task> {
    var taskList = this.map {
        Task(it.name, it.description)
    }
    return taskList
}
