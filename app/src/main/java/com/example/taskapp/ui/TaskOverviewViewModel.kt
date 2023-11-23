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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class TaskOverviewViewModel(private val tasksRepository: TasksRepository) : ViewModel() {
    // use StateFlow (Flow: emits current state + any updates)
    /*
    * Note: uiState is a cold flow. Changes don't come in from above unless a
    * refresh is called...
    * */
    private val _uiState = MutableStateFlow(TaskOverviewState(/*TaskSampler.getAll()*/))

    /*
    * Note: uiListState is a hot flow (.stateIn makes it so) --> it updates given a scope (viewmodelscope)
    * when no updates are required (lifecycle) the subscription is stopped after a timeout
    * */
    lateinit var uiListState : StateFlow<TaskListState>



    val uiState: StateFlow<TaskOverviewState> = _uiState.asStateFlow()

    // keeping the state of the api request
    var taskApiState: TaskApiState by mutableStateOf(TaskApiState.Loading)
        private set

    init {
        //initializes the uiListState
        getRepoTasks()
    }


    fun addTask() {

        //saving the new task (to db? to network? --> doesn't matter)
        viewModelScope.launch { saveTask(Task(_uiState.value.newTaskName, _uiState.value.newTaskDescription)) }

        //reset the input fields
        _uiState.update {
                currentState ->
            currentState.copy(
                /*currentTaskList = currentState.currentTaskList +
                    Task(currentState.newTaskName, currentState.newTaskDescription),*/
                // clean up previous values
                newTaskName = "",
                newTaskDescription = "",
                // whenever this changes, scrollToItemIndex should be scrolled into view
                scrollActionIdx = currentState.scrollActionIdx.plus(1),
                scrollToItemIndex = uiListState.value.taskList.size,
            )
        }
    }
    private fun validateInput(): Boolean{
        return with(_uiState){
            value.newTaskName.length > 0 && value.newTaskDescription.length > 0
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

    //this
    private fun getRepoTasks(){
        try {
            viewModelScope.launch { tasksRepository.refresh() }
            uiListState = tasksRepository.getTasks().map { TaskListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = TaskListState()
                )
            taskApiState = TaskApiState.Success
            }
            catch (e: IOException){
                //show a toast? save a log on firebase? ...
                //set the error state
                taskApiState = TaskApiState.Error
            }
    }

    private suspend fun saveTask(task: Task){
        if(validateInput())
            tasksRepository.insertTask(task)
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


