package com.example.tvappcompose.di

import android.app.Application
import androidx.room.Room
import com.example.tvapp.data.ShowDatabase
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
    fun provideDatabase(application: Application): ShowDatabase {
        return Room.databaseBuilder(application.applicationContext,
            ShowDatabase::class.java,"myshow2_database").build()
    }

}