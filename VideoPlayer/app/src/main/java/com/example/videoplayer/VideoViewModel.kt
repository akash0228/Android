package com.example.videoplayer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) :AndroidViewModel(application){
    private val videoRepo=VideoRepo(application)
    private val _videoList = MutableLiveData<List<Video>>()
    var videoList:LiveData<List<Video>> =videoRepo.videoList

    private val _searchResults = MutableLiveData<List<Video>>()
    val searchResults: LiveData<List<Video>> = _searchResults

    init {
        viewModelScope.launch {
            videoRepo.getAllVideos()
        }
    }

    fun insert(video: Video){
        viewModelScope.launch {
            videoRepo.insertVideo(video)
        }
    }

    fun update(video: Video){
        viewModelScope.launch {
            videoRepo.updateVideo(video)
        }
    }

    fun delete(video: Video){
        viewModelScope.launch {
            videoRepo.deleteVideo(video)
        }
    }

    fun getAllVideos():LiveData<List<Video>>{
        return videoList
    }

    fun getFavVideos():LiveData<List<Video>>{
        return videoList.map { videos-> videos.filter { video -> video.isFavourite } }
    }

//    fun searchVideo(query:String){
//        viewModelScope.launch {
//            videoRepo.searchVideo(query)
//        }
//    }

}