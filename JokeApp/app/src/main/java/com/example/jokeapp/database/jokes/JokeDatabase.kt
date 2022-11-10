package com.example.jokeapp.database.jokes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Joke::class], version = 1, exportSchema = false)
abstract class JokeDatabase : RoomDatabase() {

    abstract val jokeDatabaseDao: JokeDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: JokeDatabase? = null

        fun getInstance(context: Context): JokeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JokeDatabase::class.java,
                        "custom_joke_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}