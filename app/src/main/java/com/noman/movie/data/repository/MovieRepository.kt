package com.noman.movie.data.repository

import android.content.Context
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.remote.dto.MovieDetails
import com.noman.movie.data.remote.dto.MovieResponse
import com.noman.movie.utils.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository {

    suspend fun getMovieDetails(movieID: String): com.noman.movie.utils.Result<MovieDetails>

    fun getAllMoviesFromDataBase(): List<Movie>

    fun getMoviesDetailsFromDataBase(movieID: Int): MovieDetails

    suspend fun getMoviesFromApi(): Response<MovieResponse>

    suspend fun searchMovies(searchQuery: String): Response<MovieResponse>

    fun observeConnectivity(context: Context): Flow<ConnectivityObserver.Status>

}