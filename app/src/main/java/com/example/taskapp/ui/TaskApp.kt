package com.example.taskapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskapp.R
import com.example.taskapp.ui.theme.TaskAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskApp(navController: NavHostController = rememberNavController()) {
    var addingVisible by rememberSaveable { mutableStateOf(false) }

    val backStackEntry by navController.currentBackStackEntryAsState()

    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    val goHome: () -> Unit = {
        navController.popBackStack(
            TaskOverviewScreen.Start.name,
            inclusive = false,
        )
    }
    val goToAbout = { navController.navigate(TaskOverviewScreen.About.name) }

    val currentScreenTitle = TaskOverviewScreen.valueOf(
        backStackEntry?.destination?.route ?: TaskOverviewScreen.Start.name,
    ).title

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TaskAppAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                currentScreenTitle = currentScreenTitle,
            )
        },
        bottomBar = {
            TaskBottomAppBar(goHome, goToAbout)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { addingVisible = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = TaskOverviewScreen.Start.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = TaskOverviewScreen.Start.name) {
                // refactor: move the task overview to a separate composable
                // note: making the tasks clickable will be for the next lesson!
                // it requires a viewModel 🤩
                TaskOverview(addingVisible = addingVisible, { visible -> addingVisible = visible })
            }
            composable(route = TaskOverviewScreen.Detail.name) {
                Text("Detail")
            }
            composable(route = TaskOverviewScreen.About.name) {
                Text(text = "about")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskAppPreview() {
    TaskAppTheme {
        val image = painterResource(R.drawable.backgroundimage)
        // create a box to overlap image and texts
        Surface(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                alpha = 0.5F,
            )
            TaskApp()
        }
    }
}
