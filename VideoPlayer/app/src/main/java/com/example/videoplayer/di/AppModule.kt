package com.example.videoplayer.di

import android.app.Application
import androidx.room.Room
import com.example.videoplayer.data.database.VideoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}