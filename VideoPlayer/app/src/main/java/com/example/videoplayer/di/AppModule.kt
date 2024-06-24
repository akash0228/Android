package com.example.videoplayer.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.videoplayer.data.database.VideoDatabase
import com.example.videoplayer.data.repositories.VideoRepo
import com.example.videoplayer.ui.viewmodels.VideoViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application):VideoDatabase{
        return Room.databaseBuilder(application.applicationContext,
            VideoDatabase::class.java,"video_database").build()
    }

    @Provides
    @Singleton
    fun provideVideoRepo( videoDatabase:VideoDatabase):VideoRepo{
        return VideoRepo(videoDatabase)
    }



}