package com.example.taskapp.ui.aboutScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AboutScreen(vm: AboutViewModel = viewModel()) {
    Text(text = "about")
}
