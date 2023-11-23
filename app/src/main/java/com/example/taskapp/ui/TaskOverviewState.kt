package com.example.taskapp.ui

import com.example.taskapp.model.Task

// the data class just holds the (immutable) values of the state
data class TaskOverviewState(
    //val currentTaskList: List<Task>,
    val newTaskName: String = "",
    val newTaskDescription: String = "",
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)


data class TaskListState(val taskList: List<Task> = listOf())




//the sealed interface has only three possible values
/*Sidenote: to learn more about this TaskApiState object, you can search on LCE (Loading, Content, Error) pattern

When the state is changed to Error, the taskList will not be updated (offline first).
To ensure the list is considered immutable (fully immutable, won't ever change unless a new object is created), add the Immutable annotation.

The LCE pattern is not completed in the application, because it requires more complex helper classes
An example can be found here https://www.valueof.io/blog/compose-ui-state-flow-offline-first-repository
*/


sealed interface TaskApiState{
    object Success : TaskApiState
    object Error: TaskApiState
    object Loading : TaskApiState
}
