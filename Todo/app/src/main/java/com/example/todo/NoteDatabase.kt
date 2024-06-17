package com.example.todo


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Note::class), version =1)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun NoteDao():NoteDao

    companion object{
        @Volatile
        private var INSTANCE:NoteDatabase?=null
        fun getInstance(context: Context): NoteDatabase{
            return INSTANCE?: synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,"note_database").build()
                INSTANCE=instance
                instance
            }
        }
    }
}