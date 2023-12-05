package com.example.taskapp.network

import com.example.taskapp.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiTask(
    val name: String,
    val desc: String,
)

// extension function for an ApiTask List to convert is to a Domain Task List
fun Flow<List<ApiTask>>.asDomainObjects(): Flow<List<Task>> {
    return map {
        it.asDomainObjects()
    }
}

fun List<ApiTask>.asDomainObjects(): List<Task> {
    var domainList = this.map {
        Task(it.name, it.desc)
    }
    return domainList
}
