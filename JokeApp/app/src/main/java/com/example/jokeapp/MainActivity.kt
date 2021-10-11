package com.example.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.jokeapp.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //private lateinit var myJokeBook : JokeBook

    private val myJokeBook = JokeBook("")

    /*private lateinit var myButton: Button
    private lateinit var happyButton: Button
    private lateinit var jokeText: TextView
    private lateinit var happyImage: ImageView*/

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.jokes = myJokeBook

        setOnClickListeners()

    }

    //set clicklistener for each element in the list
    private fun setOnClickListeners() {
        var clickableElements = listOf(
            binding.happyButton,
            binding.nextjokeButton
        )
        for (item in clickableElements){
            when(item.id){
                R.id.nextjoke_button -> item.setOnClickListener { changeJoke() }
                R.id.happy_button -> item.setOnClickListener { happy() }
            }
        }
    }

    private fun happy() {
        val drawableResource = when (Random.nextInt(3)) {
            0 -> R.drawable.ic_iconmonstr_smiley_1
            1 -> R.drawable.ic_iconmonstr_smiley_13
            else -> R.drawable.ic_iconmonstr_smiley_2

        }
        binding.happyImage.setImageResource(drawableResource)
    }

    private fun changeJoke() {
        //binding.jokeTextview.text = myJokeBook.getRandomJoke()
        myJokeBook.changeCurrentJoke()
        binding.invalidateAll() //important! this binds the new data.
    }

}