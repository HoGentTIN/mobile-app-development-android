package com.example.taskapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.taskapp.R

enum class TaskOverviewScreen(@StringRes val title: Int, val icon: ImageVector) {
    Start(title = R.string.app_name, icon = Icons.Filled.Check),
    Detail(title = R.string.detail, Icons.Filled.Check),
    About(title = R.string.about_title, icon = Icons.Filled.Info),
}
