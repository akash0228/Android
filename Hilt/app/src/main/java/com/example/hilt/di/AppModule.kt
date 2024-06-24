package com.example.hilt.di

import android.app.Application
import com.example.hilt.data.ReposiotyImplementation
import com.example.hilt.data.ReposiotyImplementation2
import com.example.hilt.data.SomeApi
import com.example.hilt.data.SomeApi2
import com.example.hilt.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun ProvideApi():SomeApi{
        return Retrofit.Builder().baseUrl("https://someapi.com").build().create(SomeApi::class.java)
    }
    @Provides
    @Singleton
    fun ProvideApi2():SomeApi2{
        return Retrofit.Builder().baseUrl("https://test.com").build().create(SomeApi2::class.java)
    }


    //use bind instead of provide for repository
//    @Provides
//    @Singleton
//    @Named("api1") //naming so same instance conflict error should not come for different implementation of same interface
//    fun provideRepository(api:SomeApi):Repository{
//        return ReposiotyImplementation(api)
//    }
//    @Provides
//    @Singleton
//    @Named("api2")
//    fun provideRepository2(api2: SomeApi2,application: Application):Repository{
//        return ReposiotyImplementation2(api2,application)
//    }

}