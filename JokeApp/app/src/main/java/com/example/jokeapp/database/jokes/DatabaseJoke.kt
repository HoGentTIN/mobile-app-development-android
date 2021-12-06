package com.example.jokeapp.database.jokes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jokeapp.domain.Joke
import com.example.jokeapp.network.ApiJoke
import com.squareup.moshi.Json
import kotlin.random.Random

/*
* Database entity DatabaseJoke
* This represents a Joke in the database
* it also has a function to transform a list of database jokes
* to the jokes used in the rest of the app: model jokes
* */


@Entity(tableName = "custom_joke_table")
data class DatabaseJoke(
    @PrimaryKey(autoGenerate = true)
    var jokeId: Long = 0L,

    @ColumnInfo(name = "joke_setup")
    @Json(name = "setup") var jokeSetup: String = "",

    @ColumnInfo(name = "joke_type")
    @Json(name = "type") var jokeType: String = "",

    @ColumnInfo(name = "joke_punchline")
    var punchline: String = "",

    )


//convert Joke to ApiJoke
fun List<DatabaseJoke>.asDomainModel() : List<Joke>{
    return map {
        Joke(
            punchline = it.punchline,
            jokeSetup = it.jokeSetup,
            jokeType = it.jokeType
        )
    }
}


