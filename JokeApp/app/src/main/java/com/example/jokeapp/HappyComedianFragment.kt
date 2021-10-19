package com.example.jokeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.jokeapp.databinding.FragmentHappyComedianBinding


/**
 * A simple [Fragment] subclass.
 * Use the [HappyComedianFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HappyComedianFragment : Fragment() {

    lateinit var binding: FragmentHappyComedianBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_happy_comedian, container, false)

        setClickListeners()

        val args = HappyComedianFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "There were ${args.numHappyJokes} happy jokes and ${args.numUnhappyJokes} bad jokes", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    private fun setClickListeners() {
        binding.homeButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_happyComedian_to_homeFragment2)
        )

        binding.startAgainButton.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_happyComedian_to_jokeFragment)
        )
    }


}