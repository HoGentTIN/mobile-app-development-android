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
import com.google.android.material.chip.Chip


/**
 * A simple [Fragment] subclass.
 * Use the [JokeOverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JokeOverviewFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var  binding : FragmentJokeOverviewBinding
    lateinit var viewModel: JokeOverviewViewModel
    lateinit var adapter: JokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke_overview, container,false)

        //setup the db connection
        val application = requireNotNull(this.activity).application
        val dataSource = JokeDatabase.getInstance(application).jokeDatabaseDao



        //filling the list: joke adapter
        adapter = JokeAdapter( JokesListener{
                jokeID ->
            Toast.makeText(context, "${jokeID}", Toast.LENGTH_SHORT).show()
        })
        binding.jokeList.adapter = adapter

        //viewmodel
        val viewModelFactory = JokeOverviewViewModelFactory(dataSource, application, adapter)
        viewModel = ViewModelProvider(this, viewModelFactory).get(JokeOverviewViewModel::class.java)


        //databinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        //list changed
        viewModel.jokes.observe(viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })


        createChips(listOf("<10", "10-20", ">20"))
        return binding.root
    }


    private fun createChips(data : List<String>) {
        //take care: the example in the movies has dynamic chips
        //these are hardcoded.
        val chipGroup = binding.chipList
        val inflator = LayoutInflater.from(chipGroup.context)

        val children = data.map {
            text ->
            val chip = inflator.inflate(R.layout.chip, chipGroup, false) as Chip
            chip.text = text
            chip.tag = text
            chip.setOnCheckedChangeListener {
            button, isChecked ->
                viewModel.filterChip(button.tag as String, isChecked)
            }
            chip
        }

        //remove existing children
        chipGroup.removeAllViews()

        //add the new children
        for(chip in children){
            chipGroup.addView(chip)
        }
    }
}