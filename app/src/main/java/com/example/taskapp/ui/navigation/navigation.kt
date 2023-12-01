package com.example.taskapp.ui.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskapp.ui.aboutScreen.AboutScreen
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
            Log.i("vm inspection", "Nav to TaskOverview")
            TaskOverview(isAddingVisisble = fabActionVisible, makeInvisible = fabResetAction)
        }
        composable(route = TaskOverviewScreen.Detail.name) {
            Log.i("vm inspection", "Nav to detail")
            Text("Detail")
        }
        composable(route = TaskOverviewScreen.About.name) {
            Log.i("vm inspection", "Nav to about")
            AboutScreen()
        }
    }
}