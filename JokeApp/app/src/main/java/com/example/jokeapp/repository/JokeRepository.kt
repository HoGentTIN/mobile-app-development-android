package com.example.jokeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.database.jokes.asDomainModel
import com.example.jokeapp.domain.Joke
import com.example.jokeapp.network.ApiJoke
import com.example.jokeapp.network.JokeApi
import com.example.jokeapp.network.JokeApi.mockPutJoke
import com.example.jokeapp.network.asDatabaseJoke
import com.example.jokeapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.random.Random

/*
* Class to connect the db and the network
* Contains a LiveData object with jokes
* Can refresh jokes
* */
class JokeRepository(private val database: JokeDatabase) {

    //Network call
    //get jokes from the database, but transform them with map
    val jokes: LiveData<List<Joke>> =
        Transformations.map(database.jokeDatabaseDao.getAllJokesLive()){
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
            Timber.i("end suspend")
        }
    }

    //create a new joke + return the resulting joke
    suspend fun createJoke(newJoke: Joke): Joke {
        //create a Data Transfer Object (Dto)
        val newApiJoke = ApiJoke(
            jokeSetup = newJoke.jokeSetup,
            punchline = newJoke.punchline,
            jokeType = newJoke.jokeType)
        //use retrofit to put the joke.
        //a put function usually returns the object that was put

        //val checkApiJoke = JokeApi.retrofitService.putJoke(newApiJoke).await()
        val checkApiJoke = JokeApi.retrofitService.mockPutJoke(newApiJoke)

        //the put results in a JokeApi object
        //when the put is done, update the local db as well
        database.jokeDatabaseDao.insert(checkApiJoke.asDatabaseJoke())
        //the returned joke can be used to pass as save arg to the next fragment (e.g)
        return newJoke
    }





}