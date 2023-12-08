package com.example.taskapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskapp.ui.aboutScreen.AboutScreen
import com.example.taskapp.ui.cameraScreen.CameraScreen
import com.example.taskapp.ui.overviewScreen.TaskOverview

@Composable
fun navComponent(navController: NavHostController,
                 modifier: Modifier = Modifier,
                 fabActionVisible: Boolean = false,
                 fabResetAction : () -> Unit = {}) {
    NavHost(
        navController = navController,
        startDestination = TaskOverviewScreen.Start.name,
        modifier = modifier,
    ) {

        composable(route = TaskOverviewScreen.Start.name) {
            TaskOverview(isAddingVisisble = fabActionVisible, makeInvisible = fabResetAction)
        }
        composable(route = TaskOverviewScreen.Detail.name) {
            Text("Detail")
        }
        composable(route = TaskOverviewScreen.About.name) {
            AboutScreen()
        }
        composable(route = TaskOverviewScreen.Camera.name) {
            CameraScreen()
        }
    }
}