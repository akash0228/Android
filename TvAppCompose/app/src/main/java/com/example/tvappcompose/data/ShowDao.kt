package com.example.tvapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tvappcompose.models.Show
import kotlinx.coroutines.flow.Flow


@Dao
interface ShowDao {
    @Insert
    suspend fun insert(show: Show)
    @Update
    suspend fun update(show: Show)
    @Delete
    suspend fun delete(show: Show)

    @Query("SELECT * FROM show_table")
    fun getAllShow(): Flow<List<Show>>

    @Query("SELECT * FROM show_table WHERE id=:id")
    suspend fun getShow(id:Int):Show

    @Query("SELECT * FROM show_table WHERE isWatched=1")
    fun getRecentShow(): Flow<List<Show>>

    @Query("SELECT * FROM show_table WHERE isWatchList=1")
    fun getWatchLaterShow(): Flow<List<Show>>

    @Query("SELECT * FROM show_table")
    suspend fun getShows():List<Show>
}