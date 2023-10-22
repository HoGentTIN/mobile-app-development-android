package com.example.taskapp.data

import com.example.taskapp.model.Task
import kotlin.random.Random

object TaskSampler {
    val sampleTasks = mutableListOf(
        "clean the oven",
        "put the garbage out",
        "buy 2l milk",
        "study for Android",
        "water the plants",
        "feed the cat",
        "feed the dog",
    )

    val getAll: () -> MutableList<Task> = {
        val list = mutableListOf<Task>()
        for (item in sampleTasks) {
            list.add(Task(item, if (Random.nextInt(0, 1) == 0) { "lorem ipsum dolor sit" } else "consectetur adipiscing elit"))
        }
        list
    }
}
