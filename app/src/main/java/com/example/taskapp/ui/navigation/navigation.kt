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