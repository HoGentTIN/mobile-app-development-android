package com.example.taskapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.taskapp.ui.TaskApp
import com.example.taskapp.ui.theme.TaskAppTheme
import com.example.taskapp.ui.util.TaskNavigationType
import java.io.File

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*//added because the app grows. The dex file is a Dalvic Executable (a part of the compilation process of Android)
        //if it becomes to large, the OS has issues handling it well...
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()*/
        Log.i("vm inspection", "Main activity onCreate")
        setContent {
            TaskAppTheme {
                val image = painterResource(R.drawable.backgroundimage)
                // create a Surface to overlap image and texts
                Surface {
                    val windowSize = calculateWindowSizeClass(activity = this)
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize(),
                    )
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            TaskApp(TaskNavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            TaskApp(TaskNavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            TaskApp(navigationType = TaskNavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            TaskApp(navigationType = TaskNavigationType.BOTTOM_NAVIGATION)
                        }
                    }

                }
            }
        }
    }
}
