package com.example.taskapp.network

import com.example.taskapp.model.Task
import kotlinx.serialization.Serializable

@Serializable
data class ApiTask(val name: String, val desc: String) {
}

//extension function for an ApiTask List to convert is to a Domain Task List
fun List<ApiTask>.asDomainObjects(): List<Task> {
    var domainList = this.map {
        Task(it.name, it.desc)
    }
    return domainList
}