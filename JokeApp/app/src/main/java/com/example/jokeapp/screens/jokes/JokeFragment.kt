package com.example.jokeapp.screens.jokes

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.jokeapp.R
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.databinding.FragmentJokeBinding
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 * Use the [JokeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JokeFragment : Fragment() {

    private lateinit var binding: FragmentJokeBinding
    private lateinit var viewModel : JokeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        //Get an instance of the appContext to setup the database
        val appContext = requireNotNull(this.activity).application
        val dataSource = JokeDatabase.getInstance(appContext).jokeDatabaseDao

        //use a factory to pass the database reference to the viewModel
        val viewModelFactory = JokeViewModelFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(JokeViewModel::class.java)

        binding.jokes = viewModel

        //this call allows to automatically update the livedata
        //Meaning: no more resets or whatsoever
        binding.setLifecycleOwner (this)

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

        binding.addJokeButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.addJokeFragment)
        )

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