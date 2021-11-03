package com.example.jokeapp.screens.jokes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.jokeapp.R
import com.example.jokeapp.databinding.FragmentJokeBinding
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 * Use the [JokeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JokeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var binding: FragmentJokeBinding
    lateinit var viewModel : JokeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Step 1, use databinding to inflate the xml

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke, container, false)
        viewModel = JokeViewModel()

        binding.jokes = viewModel

        viewModel.currentJoke.observe(viewLifecycleOwner, Observer {
            newJoke -> binding.jokeTextview.text = newJoke
        })


        viewModel.shouldEvaluate.observe(viewLifecycleOwner, Observer { shouldEveluate ->
            if(shouldEveluate){

                view?.findNavController()?.navigate(JokeFragmentDirections.actionJokeFragmentToHappyComedian(viewModel.happyJokes, viewModel.badJokes))

                viewModel.evaluationComplete()
            }
        })


        setOnClickListeners()

        return binding.root
    }

    //set click listener for each element in the list
    private fun setOnClickListeners() {
        val clickableElements = listOf(
            binding.happyButton,
            binding.nextjokeButton
        )
        for (item in clickableElements){
            when(item.id){
                R.id.nextjoke_button -> item.setOnClickListener { viewModel.badJoke(); viewModel.changeCurrentJoke()}
                R.id.happy_button -> item.setOnClickListener { showHappySmiley(); viewModel.goodJoke(); viewModel.changeCurrentJoke()}
            }
        }
    }


    private fun showHappySmiley() {
        val drawableResource = when (Random.nextInt(3)) {
            0 -> R.drawable.ic_iconmonstr_smiley_1
            1 -> R.drawable.ic_iconmonstr_smiley_13
            else -> R.drawable.ic_iconmonstr_smiley_2
        }
        binding.happyImage.setImageResource(drawableResource)
    }

}