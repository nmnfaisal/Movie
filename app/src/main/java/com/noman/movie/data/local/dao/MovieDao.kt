package com.noman.movie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.remote.dto.MovieDetails

@Dao
interface MovieDao{

    @Query("SELECT * FROM MOVIES")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM MOVIE_DETAILS WHERE id = :movieId LIMIT 1")
    fun getMovieDetailsByID(movieId: Int): MovieDetails

    @Query("DELETE FROM MOVIES")
    fun removeAll()

    @Query("SELECT COUNT(*) FROM MOVIES")
    fun getRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movie: MovieDetails)
}