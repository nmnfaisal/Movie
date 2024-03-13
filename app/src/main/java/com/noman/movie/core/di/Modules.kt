package com.noman.movie.core.di

import android.content.Context
import androidx.room.Room
import com.noman.movie.data.local.dao.MovieDao
import com.noman.movie.data.local.database.AppDatabase
import com.noman.movie.data.remote.client.MovieService
import com.noman.movie.data.remote.client.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun providesMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDao(
        @ApplicationContext appContext
        : Context
    ): AppDatabase {
        val dbInstance = Room.databaseBuilder<AppDatabase>(
            appContext,
            AppDatabase::class.java,
            "MovieDatabase"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        return dbInstance
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}