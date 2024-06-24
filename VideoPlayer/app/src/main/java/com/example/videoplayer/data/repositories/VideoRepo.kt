package com.example.videoplayer.data.repositories

import android.content.Context
import com.example.videoplayer.data.entities.Video
import com.example.videoplayer.data.database.VideoDatabase
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoRepo @Inject constructor(private val videoDatabase:VideoDatabase) {
    private val videoDao=videoDatabase.VideoDao()
    val videoList=videoDao.getAllVideos()

    suspend fun insertVideo(video: Video){
        withContext(Dispatchers.IO){
            videoDao.Insert(video)
        }
    }

    suspend fun updateVideo(video: Video){
        withContext(Dispatchers.IO){
            videoDao.update(video)
        }
    }

    suspend fun deleteVideo(video: Video){
        withContext(Dispatchers.IO){
            videoDao.Delete(video)
        }
    }

    suspend fun getAllVideos(){
        withContext(Dispatchers.IO){
            videoDao.getAllVideos()
        }
    }
}