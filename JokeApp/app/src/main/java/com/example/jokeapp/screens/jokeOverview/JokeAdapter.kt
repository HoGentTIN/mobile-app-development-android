package com.example.jokeapp.screens.jokeOverview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jokeapp.R
import com.example.jokeapp.database.jokes.Joke

class JokeAdapter : ListAdapter<Joke, ViewHolder>(JokeDiffCallback()){
    //taken care of by ListAdapter
    /*var data = listOf<Joke>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }*/

    /*
    override fun getItemCount(): Int {
        return data.size
    }*/

    //fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
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
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.joke_list_item, parent, false)
            return ViewHolder(view)
        }
    }
}


class JokeDiffCallback: DiffUtil.ItemCallback<Joke>(){
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.jokeId == newItem.jokeId
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
        //works perfectly because it's a dataclass.
    }
}