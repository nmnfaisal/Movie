package com.noman.movie.data.local.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noman.movie.data.local.dao.MovieDao
import com.noman.movie.data.remote.dto.Movie
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java ).allowMainThreadQueries().build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadMovie() = runBlocking {
        val movie = Movie(
            true,
            "backdrop_path",
            123456,
            "en",
            "Original Title",
            "Overview of the movie",
            7.5,
            "poster_path",
            "2024-03-11",
            "Title",
            true,
            7.8,
            1000,
            byteArrayOf(1, 2, 3, 4, 5)
        )
        movieDao.insertMovie(movie)
        val movies = movieDao.getAllMovies()
        assertTrue(movies.contains(movie))
    }
}