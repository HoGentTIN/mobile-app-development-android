package com.example.taskapp.ui

import androidx.annotation.StringRes
import com.example.taskapp.R

enum class TaskOverviewScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Detail(title = R.string.detail),
    About(title = R.string.about_title),
}
