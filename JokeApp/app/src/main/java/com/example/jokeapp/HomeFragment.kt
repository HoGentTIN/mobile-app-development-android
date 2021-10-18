package com.example.jokeapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.jokeapp.databinding.FragmentHomeBinding
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Step 1, use databinding to inflate the xml

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setOnClickListeners()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun setOnClickListeners() {
        /*
        binding.startjokesButton.setOnClickListener {
            //Toast.makeText(activity, "Let's navigate!", Toast.LENGTH_SHORT).show()
            view -> view.findNavController().navigate(R.id.action_homeFragment2_to_jokeFragment)
         }
         */

        //alternatively
        binding.startjokesButton.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment2_to_jokeFragment)
        )
    }


}