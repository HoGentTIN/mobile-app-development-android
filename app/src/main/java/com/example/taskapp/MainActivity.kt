package com.example.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.taskapp.ui.TaskApp
import com.example.taskapp.ui.theme.TaskAppTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*//added because the app grows. The dex file is a Dalvic Executable (a part of the compilation process of Android)
        //if it becomes to large, the OS has issues handling it well...
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()*/
        setContent {
            TaskAppTheme {
                val image = painterResource(R.drawable.backgroundimage)
                // create a Surface to overlap image and texts
                Surface {
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize(),
                    )
                    TaskApp()
                }
            }
        }
    }
}
