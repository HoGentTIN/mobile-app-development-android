package com.example.jokeapp.database.jokes


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface JokeDatabaseDao {

    @Insert
    suspend fun insert(joke: Joke)

    @Update
    suspend fun update(joke: Joke)

    @Query("SELECT * from custom_joke_table WHERE jokeId = :key")
    suspend fun get(key: Long): Joke?

    @Query("DELETE FROM custom_joke_table")
    suspend fun clear()

    @Query("SELECT * FROM custom_joke_table ORDER BY jokeId DESC")
    suspend fun getAllJokes(): List<Joke>

    //get the joke with the highest ID (last joke added)
    @Query("SELECT * FROM custom_joke_table ORDER BY jokeId DESC LIMIT 1")
    suspend fun getLastJoke(): Joke?

    //get the number of jokes present
    @Query("SELECT COUNT(*) FROM custom_joke_table")
    suspend fun numberOfJokes(): Int
}
