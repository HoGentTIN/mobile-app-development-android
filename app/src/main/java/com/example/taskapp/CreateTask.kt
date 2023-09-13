package com.example.taskapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTask(taskName: String, taskDescription: String, onTaskNameChanged: (String) -> Unit, onTaskDescriptionChanged: (String) -> Unit, onTaskSaved: () -> Unit, modifier: Modifier = Modifier) {
    /*
    * The state needs to be hoisted, this isn't just best practise, it's also required to access the data
    * */
    //var newTaskName by remember { mutableStateOf("")}
    /*
    *The variable to store the new task is removed
    * instead, it's passed down as an argument, allong with how to modify it (on...changed)
    * */

    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()){
        TextField(
            value = taskName,
            onValueChange = onTaskNameChanged,
            label = { Text("taskname") }
        )
        TextField(
            value = taskDescription,
            onValueChange = onTaskDescriptionChanged,
            label = { Text("description") }
        )
        Button(onClick =  onTaskSaved ) {
            Text("Save")
        }
    }

}


@Preview
@Composable
fun CreateTaskPreview() {
    CreateTask("todo", "descr", {}, {}, {})
}