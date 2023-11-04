package com.example.taskapp.fake

import com.example.taskapp.network.ApiTask

object FakeDataSource {
    const val nameOne = "feed dog"
    const val nameTwo = "feed cat"
    const val descriptionOne = "food is in the cellar"
    const val descriptionTwo = "food is also in the cellar"

    val tasks = listOf(
        ApiTask(nameOne, descriptionOne),
        ApiTask(nameTwo, descriptionTwo))
}