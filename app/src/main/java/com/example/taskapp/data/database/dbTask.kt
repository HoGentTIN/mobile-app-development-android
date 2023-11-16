package com.example.taskapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tasks")
data class dbTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val isDone : Boolean = false
)
