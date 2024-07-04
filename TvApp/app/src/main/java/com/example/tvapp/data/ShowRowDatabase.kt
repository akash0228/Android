package com.example.tvapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvapp.ShowRow

@Database(entities = arrayOf(ShowRow::class), version = 1)
abstract class ShowRowDatabase: RoomDatabase() {
    abstract fun showRowDao():ShowRowDao

    companion object{
        @Volatile
        private var INSTANCE: ShowRowDatabase?=null

        fun getInstance(context: Context): ShowRowDatabase {
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(context.applicationContext,
                    ShowRowDatabase::class.java,"showRow_database").build()
                INSTANCE =instance
                instance
            }
        }
    }
}