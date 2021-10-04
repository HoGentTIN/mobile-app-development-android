package com.example.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var myJokeBook : JokeBook

    lateinit var myButton: Button
    lateinit var jokeText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myJokeBook = JokeBook()
        myButton = findViewById(R.id.nextjoke_button)
        jokeText = findViewById(R.id.joke_textview)

        myButton.setOnClickListener{
            changeJoke()
        }
    }

    private fun changeJoke() {
        jokeText.text = myJokeBook.getRandomJoke()
    }

}