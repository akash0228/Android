package com.example.hilt.di

import com.example.hilt.data.ReposiotyImplementation
import com.example.hilt.data.ReposiotyImplementation2
import com.example.hilt.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    @Named("api1")
    abstract fun bindRepository1(reposiotyImplementation: ReposiotyImplementation):Repository
    @Binds
    @Singleton
    @Named("api2")
    abstract fun bindRepository2(reposiotyImplementation2: ReposiotyImplementation2):Repository
}