package com.example.jokeapp.database.jokes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_joke_table")
data class Joke(
    @PrimaryKey(autoGenerate = true)
    var jokeId: Long = 0L,

    @ColumnInfo(name = "joke_setup")
    var jokeSetup: String = "",

    @ColumnInfo(name = "joke_type")
    var jokeType: String = "",

    @ColumnInfo(name = "joke_punchline")
    var punchline: String = "",

)