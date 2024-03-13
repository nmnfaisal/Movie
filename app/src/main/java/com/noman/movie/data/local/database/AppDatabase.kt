package com.noman.movie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noman.movie.data.local.dao.MovieDao
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.remote.dto.MovieDetails
import javax.inject.Singleton

@Database(entities = [Movie::class, MovieDetails::class], version = 2)
@Singleton
abstract class AppDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao

}