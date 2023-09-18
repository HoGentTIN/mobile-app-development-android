package com.example.taskapp

import androidx.annotation.StringRes

enum class TaskOverviewScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Detail(title = R.string.detail),
    About(title = R.string.about_title)
}