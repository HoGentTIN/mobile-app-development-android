package com.example.taskapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbTask)

    @Update
    suspend fun update(item: dbTask)

    @Delete
    suspend fun delete(item: dbTask)

    @Query("SELECT * from tasks WHERE name = :name")
    fun getItem(name: String): Flow<dbTask>

    @Query("SELECT * from tasks ORDER BY name ASC")
    fun getAllItems(): Flow<List<dbTask>>
}