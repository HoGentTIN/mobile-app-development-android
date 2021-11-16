package com.example.jokeapp.screens.addJoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.jokeapp.R
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.databinding.FragmentAddJokeBinding
import com.example.jokeapp.screens.jokes.JokeFragmentDirections
import com.example.jokeapp.screens.jokes.JokeViewModelFactory

class AddJokeFragment : Fragment()  {

    lateinit var binding : FragmentAddJokeBinding
    lateinit var viewModel : AddJokeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_joke, container, false)

        //Get an instance of the appContext to setup the database
        val appContext = requireNotNull(this.activity).application
        val dataSource = JokeDatabase.getInstance(appContext).jokeDatabaseDao

        //use a factory to pass the database reference to the viewModel
        val viewModelFactory = AddJokeViewModelFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddJokeViewModel::class.java)

        binding.viewModel = viewModel

        //this call allows to automatically update the livedata
        //Meaning: no more resets or whatsoever
        binding.setLifecycleOwner (this)

        viewModel.saveEvent.observe(viewLifecycleOwner, Observer {
            saveEvent -> if(saveEvent){
                viewModel.saveJoke(binding.editTextTextPersonName.text.toString())
                //navigate back to the joke screen
               view?.findNavController()?.navigate(AddJokeFragmentDirections.actionAddJokeFragmentToJokeFragment())
                viewModel.saveEventDone()
            }
        })


        return binding.root

    }

}