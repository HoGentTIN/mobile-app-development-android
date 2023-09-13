package com.example.taskapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskapp.ui.theme.TaskAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskAppTheme {

                val image = painterResource(R.drawable.backgroundimage)
                //create a Surface to overlap image and texts
                Surface (modifier = Modifier.fillMaxWidth()){
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                        //alpha = 0.4F
                    ) 
                    TaskApp()

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskApp() {
    var addingVisible by remember{mutableStateOf(false)}
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Tasks")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Home, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {addingVisible = true}) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        //added state, but didn't use remember, this will trigger on each recompose
        //more on lists and viewmodels will follow later!
        val tasks = mutableStateOf(data.Task.getAll())

        Box(modifier = Modifier){
            Column(modifier = Modifier.padding(innerPadding)) {
                for(item in tasks.value) {
                    Task(name = item.name, item.description)
                }
            }
            //on top of the list: the input fields
            var newTaskName by remember { mutableStateOf("") }
            var newTaskDescription by remember { mutableStateOf("") }

            if(addingVisible) {
                CreateTask(taskName = newTaskName, taskDescription = newTaskDescription,
                    onTaskNameChanged = { name -> newTaskName = name },
                    onTaskDescriptionChanged = { description -> newTaskDescription = description },
                    onTaskSaved = { //this might be useful later
                        data.Task.sampleTasks.add(newTaskName)
                        addingVisible = false
                        newTaskName = ""
                        newTaskDescription = ""
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskAppTheme {
        val image = painterResource(R.drawable.backgroundimage)
        //create a box to overlap image and texts
        Surface (modifier = Modifier.fillMaxWidth()){
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                alpha = 0.5F
            )
            TaskApp()
        }
    }
}