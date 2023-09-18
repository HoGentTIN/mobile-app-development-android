package com.example.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

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
    val navController: NavHostController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TaskAppAppBar(
                currentScreen= TaskOverviewScreen.valueOf(
                    backStackEntry?.destination?.route ?: TaskOverviewScreen.Start.name
                ),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp= {navController.navigateUp()}

            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                actions = {
                    IconButton(onClick = { navController.navigate(TaskOverviewScreen.Start.name) }) {
                        Icon(Icons.Filled.Check, contentDescription = "navigate to home screen")
                    }

                    IconButton(onClick = { navController.navigate(TaskOverviewScreen.About.name) }) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "navigate to about page",
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

        NavHost(navController = navController,
            startDestination = TaskOverviewScreen.Start.name,
            modifier = Modifier.padding(innerPadding)){
            composable(route = TaskOverviewScreen.Start.name){
                //refactor: move the task overview to a separate composable
                //note: making the tasks clickable will be for the next lesson!
                //it requires a viewModel 🤩
                TaskOverview( addingVisible = addingVisible, onVisibilityChanged = {visible -> addingVisible = visible})

            }
            composable(route = TaskOverviewScreen.Detail.name){
                Text("Detail")
            }
            composable(route = TaskOverviewScreen.About.name){
                Text(text = "about")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAppAppBar(
    currentScreen: TaskOverviewScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},

    modifier: Modifier = Modifier){
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),

        title = {
            Text(stringResource(id = currentScreen.title))
        },
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "navigate back"
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
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