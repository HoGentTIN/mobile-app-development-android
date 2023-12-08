package com.example.taskapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskapp.R
import com.example.taskapp.ui.components.NavigationDrawerContent
import com.example.taskapp.ui.components.TaskAppAppBar
import com.example.taskapp.ui.components.TaskBottomAppBar
import com.example.taskapp.ui.components.TaskNavigationRail
import com.example.taskapp.ui.navigation.TaskOverviewScreen
import com.example.taskapp.ui.navigation.navComponent
import com.example.taskapp.ui.overviewScreen.TaskOverviewViewModel
import com.example.taskapp.ui.theme.TaskAppTheme
import com.example.taskapp.ui.util.TaskNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskApp(navigationType: TaskNavigationType,
            navController: NavHostController = rememberNavController()
    ) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    val goHome: () -> Unit = {
        navController.popBackStack(
            TaskOverviewScreen.Start.name,
            inclusive = false,
        )
    }
    val goToAbout = { navController.navigate(TaskOverviewScreen.About.name)  {launchSingleTop = true} }
    val goToCamera = { navController.navigate(TaskOverviewScreen.Camera.name) {launchSingleTop = true} }
    val currentScreenTitle = TaskOverviewScreen.valueOf(
        backStackEntry?.destination?.route ?: TaskOverviewScreen.Start.name,
    ).title

    var isAddNewVisible by remember{mutableStateOf(false)}

    //Only use scaffold in compact mode
    if(navigationType == TaskNavigationType.PERMANENT_NAVIGATION_DRAWER){
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {

                NavigationDrawerContent(
                    selectedDestination = navController.currentDestination,
                    onTabPressed = {node: String -> navController.navigate(node)},
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(dimensionResource(R.dimen.drawer_padding_content))
                )
            }
        }){

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TaskAppAppBar(
                        canNavigateBack = canNavigateBack,
                        navigateUp = navigateUp,
                        currentScreenTitle = currentScreenTitle,
                    )
                },
                floatingActionButton = {

                    FloatingActionButton(onClick = { isAddNewVisible = !isAddNewVisible}) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
                //modifier = Modifier.padding(dimensionResource(id = R.dimen.drawer_width), 0.dp, 0.dp, 0.dp )
            ) { innerPadding ->

                navComponent(navController = navController, modifier = Modifier.padding(innerPadding),
                    fabActionVisible = isAddNewVisible, fabResetAction = {isAddNewVisible = false})
            }
        }
    }
    else if(navigationType == TaskNavigationType.BOTTOM_NAVIGATION) {
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

                TaskBottomAppBar(goHome, goToAbout, goToCamera)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { isAddNewVisible = !isAddNewVisible }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            },
        ) { innerPadding ->
            navComponent(navController, modifier = Modifier.padding(innerPadding))
        }
    }
    else{
        Row {
            AnimatedVisibility(visible = navigationType == TaskNavigationType.NAVIGATION_RAIL) {
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                TaskNavigationRail(
                    selectedDestination = navController.currentDestination,
                    onTabPressed = { node: String -> navController.navigate(node) },
                )
            }
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TaskAppAppBar(
                        canNavigateBack = canNavigateBack,
                        navigateUp = navigateUp,
                        currentScreenTitle = currentScreenTitle,
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { isAddNewVisible = !isAddNewVisible }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
            ) { innerPadding ->

                navComponent(navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }



}


@Preview(showBackground = true, widthDp = 500)
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
            TaskApp(TaskNavigationType.BOTTOM_NAVIGATION)
        }
    }
}


