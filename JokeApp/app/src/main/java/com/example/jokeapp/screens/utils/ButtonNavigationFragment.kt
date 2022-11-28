package com.example.jokeapp.screens.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.jokeapp.R
import com.example.jokeapp.databinding.FragmentButtonNavigationBinding

class ButtonNavigationFragment : Fragment() {

    lateinit var binding: FragmentButtonNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_button_navigation, container, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_button_navigation,
            container,
            false
        )
        binding.button2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment2_to_jokeFragment))
        return binding.root
    }
}
