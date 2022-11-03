package com.example.jokeapp.screens.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.jokeapp.R
import com.example.jokeapp.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    /*
    Note: the HomeFragment doesn't follow the MVVM architecture to demonstrate the alternatives
    The Homefragment still uses savedInstanceState and clickListeners with DataBinding
     */

    lateinit var binding: FragmentHomeBinding
    var numberOfOncreates = 0
    var numberOfOncreateViews = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If there isn't a bundle, then it's a "fresh" start
        if (savedInstanceState != null) {
            // Get all the game state information from the bundle, set it
            numberOfOncreates = savedInstanceState.getInt("onCreates", 0)
            numberOfOncreateViews = savedInstanceState.getInt("onCreateViews", 0)

        }
        numberOfOncreates+=1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Step 1, use databinding to inflate the xml
        numberOfOncreateViews+=1
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setOnClickListeners()

        setHasOptionsMenu(true)

        Toast.makeText(context, "OnCreate: ${numberOfOncreates} - OCView: ${numberOfOncreateViews}", Toast.LENGTH_SHORT).show()

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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("onCreateViews", numberOfOncreateViews)
        outState.putInt("onCreates", numberOfOncreates)
        super.onSaveInstanceState(outState)
    }
}