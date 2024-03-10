package com.noman.movie.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.noman.movie.data.local.dao.MovieDao
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.remote.dto.MovieDetails
import com.noman.movie.data.repository.MovieDetailsRepository
import com.noman.movie.utils.Events
import com.noman.movie.utils.Result
import com.noman.movie.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel

@Inject constructor(
    private val movieRepository: MovieDetailsRepository,
    private val movieDao: MovieDao
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val _movieDetails = MutableStateFlow<Events<Result<MovieDetails>>>(Events(Result(Status.LOADING, null, null)))
    val movieDetails: StateFlow<Events<Result<MovieDetails>>> = _movieDetails
    private var isInternetAvailable: Boolean = false

    val movieList: Flow<PagingData<Movie>> = query.flatMapLatest { searchQuery ->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(searchQuery, movieRepository, isInternetAvailable)
        }.flow.cachedIn(viewModelScope)
    }

    fun setQuery(searchQuery: String) { query.value = searchQuery }

    fun setIsInternetAvailable(internet: Boolean) { isInternetAvailable = internet }

    fun getMovieDetails(movie: Movie) = viewModelScope.launch {
        val movieDetails = movieRepository.getMovieDetails(movie.id.toString())
        withContext(Dispatchers.IO) {
            try {
                movieDao.insertMovie(movie)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
//        _movieDetails.emit(Events(Result(Status.LOADING, null, null)))
        _movieDetails.emit(Events(movieDetails))
    }


}