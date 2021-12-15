package com.example.jokeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    /*
    * What if the jokes list depends on query params?
    * --> The livedata source from the db will change!
    * */

    /* -- Solution 1 -- */
    /*MediatorLiveData
    * Hold a reference to the livedata instances, and switch them when needed
    * */


    //Network call
    //get jokes from the database, but transform them with map
    val jokes= MediatorLiveData<List<Joke>>()

    //keep a reference to the original livedata
    private var changeableLiveData = Transformations.map(database.jokeDatabaseDao.getAllJokesLive()){
        it.asDomainModel()
    }

    //add the data to the mediator
    init {
        jokes.addSource(
            changeableLiveData
        ){
            jokes.setValue(it)
        }
    }

    //Filter
    fun addFilter(filter: String?){
        //remove the original source
        jokes.removeSource(changeableLiveData)
        //change the livedata object + apply filter
        changeableLiveData = when(filter){
            "<10" -> Transformations.map(database.jokeDatabaseDao.getUnder10JokesLive()){
                        it.asDomainModel()
                    }
            "10-20" -> Transformations.map(database.jokeDatabaseDao.getbetween1020JokesLive()){
                        it.asDomainModel()
                    }
            ">20" -> Transformations.map(database.jokeDatabaseDao.getgreater20JokesLive()){
                        it.asDomainModel()
                    }
            else -> Transformations.map(database.jokeDatabaseDao.getAllJokesLive()){
                    it.asDomainModel()
                }
        }
        //add the data to the mediator
        jokes.addSource(changeableLiveData){jokes.setValue(it)}
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