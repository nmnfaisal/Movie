package com.noman.movie.data.repository

import com.noman.movie.data.remote.client.MovieService
import com.noman.movie.data.remote.dto.MovieDetails
import com.noman.movie.utils.Constants
import com.noman.movie.utils.Status

class MovieDetailsRepository(private val movieService: MovieService) {

    suspend fun getMovieDetails(movieID: String): com.noman.movie.utils.Result<MovieDetails> {

        return try {
            val response = movieService.getMovieDetails(movieID, Constants.API_KEY)
            if (response.isSuccessful) {
                com.noman.movie.utils.Result(Status.SUCCESS, response.body(), null)
            } else {
                com.noman.movie.utils.Result(Status.ERROR, null, null)
            }
        } catch (e: Exception) {
            com.noman.movie.utils.Result(Status.ERROR, null, null)
        }
    }
}