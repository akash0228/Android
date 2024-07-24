package com.example.tvapp.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tvappcompose.models.Show


@Database(entities = arrayOf(Show::class), version = 1)
abstract class ShowDatabase: RoomDatabase() {
    abstract fun showDao():ShowDao
}