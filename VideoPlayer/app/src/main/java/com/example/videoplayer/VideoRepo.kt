package com.example.videoplayer

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepo(context:Context) {
    private val videoDatabase = VideoDatabase.getInstance(context)
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