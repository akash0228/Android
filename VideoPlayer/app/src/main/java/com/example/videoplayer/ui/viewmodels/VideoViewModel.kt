package com.example.videoplayer.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.data.entities.Video
import com.example.videoplayer.data.repositories.VideoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(application: Application, val videoRepo:VideoRepo):AndroidViewModel(application){
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


}