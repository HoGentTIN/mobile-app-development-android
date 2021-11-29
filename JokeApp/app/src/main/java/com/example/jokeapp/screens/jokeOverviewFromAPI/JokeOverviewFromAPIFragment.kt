package com.example.jokeapp.screens.jokeOverviewFromAPI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jokeapp.R
import com.example.jokeapp.databinding.FragmentJokeOverviewFromApiBinding

class JokeOverviewFromAPIFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentJokeOverviewFromApiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke_overview_from_api, container,false)
        val viewModel = FromAPIViewModel()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}