package com.noman.movie.ui.screens.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class MoviePaging(
    private val searchQueryFlow: String,
    private val movieRepository: MovieDetailsRepository,
    private val isInternetAvailable: Boolean
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state?.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            if (!isInternetAvailable) {
                // If internet is not available, load movies from the database
                val data = movieRepository.getAllMoviesFromDataBase()
                return LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = null
                )
            }
            val data = if (searchQueryFlow.isNotEmpty()) {
                movieRepository.searchMovies(searchQueryFlow)

            } else {
                movieRepository.getMoviesFromApi()
            }
            LoadResult.Page(
                data = data.body()?.results!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.results?.isEmpty()!!) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}