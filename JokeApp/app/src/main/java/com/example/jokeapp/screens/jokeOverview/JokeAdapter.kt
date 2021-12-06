package com.example.jokeapp.screens.jokeOverview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.example.jokeapp.database.jokes.DatabaseJoke
import com.example.jokeapp.databinding.JokeListItemBinding

class JokeAdapter(val clickListener: JokesListener) : ListAdapter<DatabaseJoke, ViewHolder>(JokeDiffCallback()){
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
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

}

//class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class ViewHolder(val binding: JokeListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener: JokesListener, item: DatabaseJoke) {
        binding.jokePunchlineTextview.text = item.punchline
        binding.jokeSetupTextview.text = item.jokeSetup

        binding.joke = item
        binding.clickListener = clickListener
        binding.executePendingBindings()


    }

    //this way the viewHolder knows how to inflate.
    //better than having this in the adapter.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = JokeListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}


class JokeDiffCallback: DiffUtil.ItemCallback<DatabaseJoke>(){
    override fun areItemsTheSame(oldItem: DatabaseJoke, newItem: DatabaseJoke): Boolean {
        return oldItem.jokeId == newItem.jokeId
    }

    override fun areContentsTheSame(oldItem: DatabaseJoke, newItem: DatabaseJoke): Boolean {
        return oldItem == newItem
        //works perfectly because it's a dataclass.
    }
}

class JokesListener(val clickListener: (jokeID: Long)->Unit){
    fun onClick(joke: DatabaseJoke) = clickListener(joke.jokeId)
}