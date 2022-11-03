package com.example.jokeapp.screens.jokes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        //Not OK: don't call the viewModel like a normal class.
        //It needs to be special to survive e.g. config changes
        //viewModel = JokeViewModel()

        viewModel = ViewModelProvider(this).get(JokeViewModel::class.java)

        binding.jokes = viewModel

        //this call allows to automatically update the livedata
        //Meaning: no more resets or whatsoever
        binding.setLifecycleOwner (this)

        //This is now no longer needed --> you can just call it from the XML
        /*viewModel.currentJoke.observe(viewLifecycleOwner, Observer {
            newJoke -> binding.jokeTextview.text = newJoke
        })*/


        viewModel.shouldEvaluate.observe(viewLifecycleOwner, Observer { shouldEveluate ->
            if(shouldEveluate){

                view?.findNavController()?.navigate(JokeFragmentDirections.actionJokeFragmentToHappyComedian(viewModel.happyJokes, viewModel.badJokes))

                viewModel.evaluationComplete()
            }
        })

        viewModel.showSmileyEvent.observe(viewLifecycleOwner, Observer {
            show -> if(show){
                showHappySmiley()
                viewModel.showImageComplete()
            }
        })

        return binding.root
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