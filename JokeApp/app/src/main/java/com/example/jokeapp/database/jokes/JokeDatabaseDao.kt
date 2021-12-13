package com.example.jokeapp.database.jokes


import androidx.lifecycle.LiveData
import androidx.room.*

/*
* Contains functions to insert and get jokes
* note: getJokesLive --> live data with a list of jokes
* note 2: insertAll --> takes a vararg jokes to update all jokes
*/

/*the Dao only knows about DatabaseJokes*/

@Dao
interface JokeDatabaseDao {

    @Insert
    suspend fun insert(joke: DatabaseJoke)

    //adding insert all with varar
    //replace strategy: upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg jokes : DatabaseJoke)

    @Update
    suspend fun update(joke: DatabaseJoke)

    @Query("SELECT * from custom_joke_table WHERE jokeId = :key")
    suspend fun get(key: Long): DatabaseJoke?

    @Query("DELETE FROM custom_joke_table")
    suspend fun clear()

    @Query("SELECT * FROM custom_joke_table ORDER BY jokeId DESC")
    suspend fun getAllJokes(): List<DatabaseJoke>

    @Query("SELECT * FROM custom_joke_table ORDER BY jokeId DESC")
    fun getAllJokesLive(): LiveData<List<DatabaseJoke>>

    //get the joke with the highest ID (last joke added)
    @Query("SELECT * FROM custom_joke_table ORDER BY jokeId DESC LIMIT 1")
    suspend fun getLastJoke(): DatabaseJoke?

    //get the number of jokes present
    @Query("SELECT COUNT(*) FROM custom_joke_table")
    suspend fun numberOfJokes(): Int
}
