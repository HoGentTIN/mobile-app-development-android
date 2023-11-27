package com.example.taskapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskapp.ui.overviewScreen.TaskOverview

@Composable
fun navComponent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = TaskOverviewScreen.Start.name,
        modifier = modifier,
    ) {

        composable(route = TaskOverviewScreen.Start.name) {
            // refactor: move the task overview to a separate composable
            // note: making the tasks clickable will be for the next lesson!
            // it requires a viewModel 🤩
            TaskOverview()
        }
        composable(route = TaskOverviewScreen.Detail.name) {
            Text("Detail")
        }
        composable(route = TaskOverviewScreen.About.name) {
            Text(text = "about")
        }
    }
}