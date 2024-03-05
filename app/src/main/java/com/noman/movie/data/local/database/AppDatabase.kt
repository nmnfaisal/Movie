package com.noman.movie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noman.movie.data.local.dao.MovieDao
import com.noman.movie.data.remote.dto.MovieDetails
import com.noman.movie.utils.IntListConverter
import javax.inject.Singleton

@Database(entities = [MovieDetails::class], version = 1)
@Singleton
//@TypeConverters(IntListConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao
}