package data

import kotlin.random.Random

data class Task (var name: String, var description: String = ""){
    companion object TaskSampler{
        val sampleTasks = mutableListOf(
            "clean the oven",
            "put the garbage out",
            "buy 2l milk",
            "study for Android",
            "water the plants",
            "feed the cat",
            "feed the dog"
            )
        val getOne: () -> Task = {Task(sampleTasks[Random.nextInt(0, sampleTasks.size)],
            if(Random.nextInt(0,1) == 0) {"lorem ipsum dolor sit"} else "consectetur adipiscing elit"
        )}

        val getAll: () -> List<Task> = {
            val list = mutableListOf<Task>()
            for(item in sampleTasks){
                list.add(Task(item, if(Random.nextInt(0,1) == 0) {"lorem ipsum dolor sit"} else "consectetur adipiscing elit" ))
            }
            list
        }
    }
}

