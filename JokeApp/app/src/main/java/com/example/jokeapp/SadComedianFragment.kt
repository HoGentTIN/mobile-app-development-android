package com.example.jokeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.jokeapp.databinding.FragmentSadComedianBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SadComedianFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SadComedianFragment : Fragment() {
    lateinit var binding: FragmentSadComedianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sad_comedian, container, false)
        return binding.root
    }

}