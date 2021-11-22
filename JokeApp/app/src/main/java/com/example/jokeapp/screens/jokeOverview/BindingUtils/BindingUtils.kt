package com.example.jokeapp.screens.jokeOverview.BindingUtils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.jokeapp.R
import com.example.jokeapp.database.jokes.Joke

//The adapter will adapt the joke to get the data we need
@BindingAdapter("jokeImage")
fun ImageView.setJokeImage(item: Joke){
    setImageResource(when (item.jokeId.mod(2)){
        0 -> R.drawable.ic_iconmonstr_smiley_2
        1 -> R.drawable.ic_iconmonstr_smiley_13
        else -> R.drawable.ic_iconmonstr_smiley_1
    })
}