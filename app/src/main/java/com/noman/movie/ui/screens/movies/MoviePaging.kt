package com.noman.movie.ui.screens.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.noman.movie.data.remote.client.MovieService
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.utils.Constants

class MoviePaging(private val searchQuery: String, private val movieService: MovieService) :
    PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state?.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val data = if (searchQuery.isNotEmpty()) {
                movieService.searchMovies(Constants.API_KEY, searchQuery)
            } else {
                movieService.getMovies(Constants.API_KEY)
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