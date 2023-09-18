package com.example.taskapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskOverview(modifier: Modifier = Modifier, addingVisible: Boolean, onVisibilityChanged: (Boolean) -> Unit) {


//added state, but didn't use remember, this will trigger on each recompose
//more on lists and viewmodels will follow later!
    val tasks = mutableStateOf(data.Task.getAll())

    Box(modifier = modifier) {
        Column() {
            for (item in tasks.value) {
                Task(name = item.name, item.description)
            }
        }
        //on top of the list: the input fields
        var newTaskName by remember { mutableStateOf("") }
        var newTaskDescription by remember { mutableStateOf("") }

        if (addingVisible) {
            CreateTask(
                taskName = newTaskName, taskDescription = newTaskDescription,
                onTaskNameChanged = { name -> newTaskName = name },
                onTaskDescriptionChanged = { description -> newTaskDescription = description },
                onTaskSaved = { //this might be useful later
                    data.Task.sampleTasks.add(newTaskName)
                    onVisibilityChanged(false)
                    newTaskName = ""
                    newTaskDescription = ""
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun Task(name: String = "", description: String = "", modifier: Modifier = Modifier) {
    Row (horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "$name",
                fontSize = 24.sp
            )
            Text(text = description,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )

        }
        Checkbox(checked = false, onCheckedChange = {})
    }
}