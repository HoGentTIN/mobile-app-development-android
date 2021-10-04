package com.example.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var myJokeBook : JokeBook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myJokeBook = JokeBook()
        val myButton : Button = findViewById(R.id.nextjoke_button)
        myButton.setOnClickListener{
            changeJoke()
        }
    }

    private fun changeJoke() {
        val jokeText: TextView = findViewById(R.id.joke_textview)
        jokeText.text = myJokeBook.getRandomJoke()
    }

}