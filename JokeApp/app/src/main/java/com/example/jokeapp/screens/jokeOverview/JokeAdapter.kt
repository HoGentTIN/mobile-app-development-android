package com.example.jokeapp.screens.jokeOverview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jokeapp.R
import com.example.jokeapp.database.jokes.Joke

class JokeAdapter : RecyclerView.Adapter<ViewHolder>(){
    var data = listOf<Joke>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    //fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

}

//class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val jokeSetup: TextView = itemView.findViewById(R.id.joke_setup_textview)
    val jokePunchline : TextView = itemView.findViewById(R.id.joke_punchline_textview)

    fun bind(item: Joke) {
        jokePunchline.text = item.punchline
        jokeSetup.text = item.jokeSetup
    }

    //this way the viewHolder knows how to inflate.
    //better than having this in the adapter.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflator = LayoutInflater.from(parent.context)
            val view = layoutInflator.inflate(R.layout.joke_list_item, parent, false)
            return ViewHolder(view)
        }
    }
}