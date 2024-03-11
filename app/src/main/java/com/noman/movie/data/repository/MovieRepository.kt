package com.noman.movie.data.repository

import android.content.Context
import com.noman.movie.data.local.dao.MovieDao
import com.noman.movie.data.remote.client.MovieService
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.remote.dto.MovieDetails
import com.noman.movie.data.remote.dto.MovieResponse
import com.noman.movie.utils.Constants
import com.noman.movie.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject


class MovieDetailsRepository
@Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,

) {

    private lateinit var movieDetails: MovieDetails

    suspend fun getMovieDetails(movieID: String): com.noman.movie.utils.Result<MovieDetails> {
        return try {
            val response = movieService.getMovieDetails(movieID, Constants.API_KEY)
            if (response.isSuccessful) {
                movieDetails = response.body()!!
                com.noman.movie.utils.Result(Status.SUCCESS, response.body(), null)
            } else {
                com.noman.movie.utils.Result(Status.ERROR, null, null)
            }
        } catch (e: Exception) {
            com.noman.movie.utils.Result(Status.ERROR, null, null)
        }
    }

    fun getAllMoviesFromDataBase(): List<Movie> {
        return movieDao.getAllMovies()
    }

    suspend fun getMoviesFromApi(): Response<MovieResponse> {
        return movieService.getMovies(Constants.API_KEY)
    }

    suspend fun searchMovies(searchQuery: String): Response<MovieResponse> {
        return movieService.searchMovies(Constants.API_KEY, searchQuery)
    }

}