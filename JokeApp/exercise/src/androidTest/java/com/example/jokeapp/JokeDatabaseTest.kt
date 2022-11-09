package com.example.jokeapp

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.example.jokeapp.database.jokes.Joke
import com.example.jokeapp.database.jokes.JokeDatabase
import com.example.jokeapp.database.jokes.JokeDatabaseDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class JokeDatabaseTest {

    private lateinit var jokeDao: JokeDatabaseDao
    private lateinit var db: JokeDatabase

    @Before
    fun createDb() {
        Log.i("before", "running before")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, JokeDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        jokeDao = db.jokeDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetJoke() = runBlocking {
        val joke = Joke()
        jokeDao.insert(joke)
        val lastJoke = jokeDao.getLastJoke()
        assertEquals(lastJoke?.punchline, "")
    }
}