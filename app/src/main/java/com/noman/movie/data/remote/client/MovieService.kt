package com.noman.movie.data.remote.client

import com.noman.movie.data.remote.dto.MovieDetails
import com.noman.movie.data.remote.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular?")
    suspend fun getMovies(
        @Query("api_key")apiKey: String,
    ):Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ):Response<MovieDetails>

    @GET("search/movie?")
    suspend fun searchMovies(
        @Query("api_key")apiKey: String,
        @Query("query") query: String
    ):Response<MovieResponse>
}