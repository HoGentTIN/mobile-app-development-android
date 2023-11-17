package com.example.taskapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskapp.TasksApplication
import com.example.taskapp.data.TaskSampler
import com.example.taskapp.data.TasksRepository
import com.example.taskapp.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class TaskOverviewViewModel(private val tasksRepository: TasksRepository) : ViewModel() {
    // use StateFlow (Flow: emits current state + any updates)
    private val _uiState = MutableStateFlow(TaskOverviewState(TaskSampler.getAll()))
    val uiState: StateFlow<TaskOverviewState> = _uiState.asStateFlow()

    // keeping the state of the api request
    var taskApiState: TaskApiState by mutableStateOf(TaskApiState.Loading)
        private set

    init {
        getApiTasks()
    }


    fun addTask() {
        _uiState.update {
                currentState ->
            currentState.copy(
                currentTaskList = currentState.currentTaskList +
                    Task(currentState.newTaskName, currentState.newTaskDescription),
                // clean up previous values
                newTaskName = "",
                newTaskDescription = "",
                // whenever this changes, scrollToItemIndex should be scrolled into view
                scrollActionIdx = currentState.scrollActionIdx.plus(1),
                scrollToItemIndex = currentState.currentTaskList.size,
            )
        }
    }

    fun setNewTaskName(newTaskName: String) {
        _uiState.update {
            it.copy(newTaskName = newTaskName)
        }
    }

    fun setNewTaskDescription(newTaskDescription: String) {
        _uiState.update {
            it.copy(newTaskDescription = newTaskDescription)
        }
    }

    private fun getApiTasks(){
        viewModelScope.launch {
            try{
                //use the repository
                //val tasksRepository = ApiTasksRepository() //repo is now injected
                val listResult = tasksRepository.getTasks()
                _uiState.update {
                    it.copy(currentTaskList = listResult)
                }
                taskApiState = TaskApiState.Success(listResult)
            }
            catch (e: IOException){
                //show a toast? save a log on firebase? ...
                //set the error state
                taskApiState = TaskApiState.Error
            }

        }
    }

    //object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TasksApplication)
                val tasksRepository = application.container.tasksRepository
                TaskOverviewViewModel(tasksRepository = tasksRepository
                )
            }
        }
    }
}


