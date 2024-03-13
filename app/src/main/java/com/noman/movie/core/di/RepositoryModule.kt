package com.noman.movie.core.di

import com.noman.movie.data.repository.MovieRepository
import com.noman.movie.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
annotation class MyRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @MyRepositoryImpl
    @Singleton
    @Binds
    abstract fun bindMovieRepositoryImpl(
         movieRepositoryImpl: MovieRepositoryImpl,
    ): MovieRepository
}