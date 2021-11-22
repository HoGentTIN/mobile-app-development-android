package com.example.jokeapp.screens.jokeOverview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jokeapp.R
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.databinding.FragmentJokeOverviewBinding


/**
 * A simple [Fragment] subclass.
 * Use the [JokeOverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JokeOverviewFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentJokeOverviewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke_overview, container,false)

        //setup the db connection
        val application = requireNotNull(this.activity).application
        val dataSource = JokeDatabase.getInstance(application).jokeDatabaseDao

        val viewModelFactory = JokeOverviewViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(JokeOverviewViewModel::class.java)

        //databinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        //filling the list: joke adapter
        val adapter = JokeAdapter( JokesListener{
            jokeID ->
            Toast.makeText(context, "${jokeID}", Toast.LENGTH_SHORT).show()
        })
        binding.jokeList.adapter = adapter


        //watch the data:
        viewModel.jokes.observe(viewLifecycleOwner, Observer{
            /*it?.let {
                adapter.data = it
            }*/
            //don't change the adapters data, use the ListAdapter feature:
            adapter.submitList(it)
        })


        return binding.root
    }
}