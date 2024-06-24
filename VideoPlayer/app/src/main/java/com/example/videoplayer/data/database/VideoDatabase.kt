package com.example.videoplayer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.videoplayer.data.daos.VideoDao
import com.example.videoplayer.data.entities.Video

@Database(entities = arrayOf(Video::class), version = 1)
abstract class VideoDatabase:RoomDatabase() {
    abstract fun VideoDao(): VideoDao

    companion object{
        @Volatile
        private var INSTANCE: VideoDatabase?=null

        fun getInstance(context: Context): VideoDatabase {
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(context.applicationContext,
                    VideoDatabase::class.java,"video_database").build()
                INSTANCE =instance
                instance
            }
        }
    }
}