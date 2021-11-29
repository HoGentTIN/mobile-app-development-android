package com.example.jokeapp.screens.BindingUtils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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

//Adapter for imageURI
@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?){
    imgUrl?.let{
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .into(this)
    }
}