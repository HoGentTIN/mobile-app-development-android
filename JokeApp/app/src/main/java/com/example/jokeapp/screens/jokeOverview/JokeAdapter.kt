package com.example.jokeapp.screens.jokeOverview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jokeapp.R
import com.example.jokeapp.database.jokes.Joke

class JokeAdapter : RecyclerView.Adapter<TextItemViewHolder>(){
    var data = listOf<Joke>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.punchline
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val view = layoutInflator.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }



}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)