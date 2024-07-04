package com.example.tvapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tvapp.ShowRow

@Dao
interface ShowRowDao {

    @Insert
    fun insert(showRow:ShowRow)
    @Update
    fun update(showRow: ShowRow)
    @Delete
    fun delete(showRow: ShowRow)

    @Query("SELECT * FROM show_row_table")
    fun getAllShowRow(): LiveData<List<ShowRow>>
}