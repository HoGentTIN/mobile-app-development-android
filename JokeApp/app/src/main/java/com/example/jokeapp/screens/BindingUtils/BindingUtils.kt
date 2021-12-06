package com.example.jokeapp.screens.BindingUtils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.jokeapp.R
import com.example.jokeapp.database.jokes.DatabaseJoke
import com.example.jokeapp.domain.Joke
import com.example.jokeapp.screens.jokeOverviewFromAPI.JokeApiStatus
import kotlin.random.Random

//The adapter will adapt the joke to get the data we need
@BindingAdapter("jokeImage")
fun ImageView.setJokeImage(item: DatabaseJoke){
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
    /*
    * possible improvement (animations): .apply(RequestOptions()
      .placeholder(R.drawable.loading_animation)
      .error(R.drawable.ic_broken_image))*/
}


//Adapt status to an image visibility
@BindingAdapter("jokeApiStatus")
fun ImageView.bindStatus( status: JokeApiStatus?) {
    when (status) {
        JokeApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_iconmonstr_smiley_13)
        }
        JokeApiStatus.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_iconmonstr_smiley_1)
        }
        JokeApiStatus.DONE -> {
            visibility = View.GONE
        }
    }
}


@BindingAdapter("jokePunchline")
fun TextView.bindPunchline(jokes: List<Joke>?){
    jokes?.let{
        text = jokes[0].punchline
    }
}

@BindingAdapter("jokeSetup")
fun TextView.bindSetup(jokes: List<Joke>?){
    jokes?.let{
        text = jokes[0].jokeSetup
    }
}

@BindingAdapter("randomJoke")
fun TextView.bindRandom(jokes: List<Joke>?){
    jokes?.let{

        var randomListNumber = Random.nextInt(it.size)
        text = jokes[randomListNumber].punchline
    }
}

