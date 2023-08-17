package com.example.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
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
                //create a box to overlap image and texts
                Box {
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = 0.4F
                    ) 
                    TaskApp()

                }
            }
        }
    }
}

@Composable
fun TaskApp() {
    //note: this requires some more attention later (state!!)
    val task = data.Task.getOne()
    Column(){
        Task(name = task.name, task.description)
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
        Box {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.5F
            )
            TaskApp()
        }
    }
}