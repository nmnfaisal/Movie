package com.noman.movie.core.di

import com.noman.movie.data.remote.client.MovieService
import com.noman.movie.data.remote.client.RetrofitClient
import com.noman.movie.data.repository.MovieDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules{

    @Provides
    @Singleton
    fun providesRetrofitClient(): Retrofit {
        return RetrofitClient.retrofit
    }

}