package com.example.jokeapp.screens.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jokeapp.R


class AboutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val aboutFragment: View = inflater.inflate(R.layout.fragment_about, container, false)

        val titletext : TextView? = aboutFragment.findViewById(R.id.title_text_view)
        titletext?.text = "My title text for About"

        return aboutFragment
    }


}