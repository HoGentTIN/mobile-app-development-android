package com.example.jokeapp.network

import android.provider.ContactsContract
import androidx.room.Database
import com.example.jokeapp.database.jokes.DatabaseJoke
import com.example.jokeapp.domain.Joke
import com.squareup.moshi.Json
import kotlinx.coroutines.Deferred

/*ApiJoke: this is a DataTransferObject
* it's goal is to get network data into our model data, the Joke
*/

/*Container helps us parse the body into multiple jokes*/
data class ApiJokeContainer (
    @Json(name = "body")
    val apiJokes: List<ApiJoke>
)

/*ApiJoke, representing a joke from the network*/
data class ApiJoke(
    @Json(name = "setup")
    var jokeSetup: String = "",

    @Json(name = "type")
    var jokeType: String = "",

    var punchline: String = "",
)

/*
* Convert network results into Domain jokes
* */
fun ApiJokeContainer.asDomainModel(): List<Joke>{
    return apiJokes.map{
        Joke(jokeSetup = it.jokeSetup,
        jokeType = it.jokeType,
        punchline = it.punchline)
    }
}

/*
* Convert network result into Database jokes
*
* returns an array that can be used in the insert call as vararg
* */
fun ApiJokeContainer.asDatabaseModel(): Array<DatabaseJoke>{
    return apiJokes.map{
        DatabaseJoke(jokeSetup = it.jokeSetup,
            jokeType = it.jokeType,
            punchline = it.punchline)
    }.toTypedArray()
}

/*
* Convert a single api joke to a database joke
* */
fun ApiJoke.asDatabaseJoke(): DatabaseJoke{
    return DatabaseJoke(
        jokeSetup = jokeSetup,
        jokeType = jokeType,
        punchline = punchline
    )
}