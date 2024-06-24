package com.example.videoplayer.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.videoplayer.data.entities.Video

@Dao
interface VideoDao {

    @Insert
    fun Insert(video: Video)

    @Update
    fun update(video: Video)

    @Delete
    fun Delete(video: Video)

    @Query("SELECT * FROM my_videos")
    fun getAllVideos():LiveData<List<Video>>
}