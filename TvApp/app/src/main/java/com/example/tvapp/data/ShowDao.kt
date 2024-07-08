package com.example.tvapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tvapp.Show

@Dao
interface ShowDao {

    @Insert
    fun insert(show:Show)
    @Update
    fun update(show: Show)
    @Delete
    fun delete(show: Show)

    @Query("SELECT * FROM show_table")
    fun getAllShow(): LiveData<List<Show>>
}