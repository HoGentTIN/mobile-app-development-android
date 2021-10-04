package com.example.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var myJokeBook : JokeBook

    lateinit var myButton: Button
    lateinit var happyButton: Button
    lateinit var jokeText: TextView
    lateinit var happyImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myJokeBook = JokeBook()
        myButton = findViewById(R.id.nextjoke_button)
        happyButton = findViewById(R.id.happy_button)
        jokeText = findViewById(R.id.joke_textview)
        happyImage = findViewById(R.id.happy_image)

        myButton.setOnClickListener{
            changeJoke()
        }

        happyButton.setOnClickListener{
            happy()
        }
    }

    private fun happy() {
        var randomInt = Random.nextInt(3)
        val drawableResource = when (randomInt) {
            0 -> R.drawable.ic_iconmonstr_smiley_1
            1 -> R.drawable.ic_iconmonstr_smiley_13
            else -> R.drawable.ic_iconmonstr_smiley_2

        }
        happyImage.setImageResource(drawableResource)
    }

    private fun changeJoke() {
        jokeText.text = myJokeBook.getRandomJoke()
    }

}