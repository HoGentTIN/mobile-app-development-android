package com.example.jokeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.database.jokes.asDomainModel
import com.example.jokeapp.domain.Joke
import com.example.jokeapp.network.JokeApi
import com.example.jokeapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
* Class to connect the db and the network
* Contains a LiveData object with jokes
* Can refresh jokes
* */
class JokeRepository(private val database: JokeDatabase) {

    //Network call
    //get jokes from the database, but transform them with map
    val jokes: LiveData<List<Joke>> = Transformations.map(database.jokeDatabaseDao.getAllJokesLive()){
        it.asDomainModel()
    }

    //Database call
    suspend fun refreshJokes(){
        //switch context to IO thread
        withContext(Dispatchers.IO){
            val jokes = JokeApi.retrofitService.getJokes().await()
            //'*': kotlin spread operator.
            //Used for functions that expect a vararg param
            //just spreads the array into separate fields
            database.jokeDatabaseDao.insertAll(*jokes.asDatabaseModel())
        }
    }
}