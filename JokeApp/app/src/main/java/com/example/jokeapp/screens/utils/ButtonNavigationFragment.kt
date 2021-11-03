package com.example.jokeapp.screens.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.jokeapp.R
import com.example.jokeapp.databinding.FragmentButtonNavigationBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ButtonNavigationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ButtonNavigationFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var binding : FragmentButtonNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_button_navigation, container, false)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_button_navigation, container, false)
        binding.button2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment2_to_jokeFragment))
        return binding.root
    }


}