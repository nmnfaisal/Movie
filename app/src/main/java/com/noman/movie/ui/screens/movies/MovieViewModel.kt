package com.noman.movie.ui.screens.movies

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.noman.movie.core.di.MyRepositoryImpl
import com.noman.movie.data.local.dao.MovieDao
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.remote.dto.MovieDetails
import com.noman.movie.data.repository.MovieRepository
import com.noman.movie.utils.ConnectivityObserver
import com.noman.movie.utils.Events
import com.noman.movie.utils.NetworkConnectivityObserver
import com.noman.movie.utils.Result
import com.noman.movie.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    @MyRepositoryImpl
    private val movieRepository: MovieRepository,
    private val movieDao: MovieDao
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val _movieDetails = MutableStateFlow<Events<Result<MovieDetails>>>(Events(Result(Status.LOADING, null, null)))
    val movieDetails: StateFlow<Events<Result<MovieDetails>>> = _movieDetails
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    private val _internetStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val internetStatus: StateFlow<ConnectivityObserver.Status> get() = _internetStatus
    private var isInternetAvailable: Boolean = false

    val movieList: Flow<PagingData<Movie>> = query.flatMapLatest { searchQuery ->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(searchQuery, movieRepository, isInternetAvailable)
        }.flow
            .cachedIn(viewModelScope)
    }

    fun setQuery(searchQuery: String) {
        query.value = searchQuery
    }

    private fun setIsInternetAvailable(internet: Boolean) {
        isInternetAvailable = internet
    }

    fun getMovieDetails(movie: Movie) = viewModelScope.launch {
        try {
            _isLoading.emit(true)
            val movieDetails = movieRepository.getMovieDetails(movie.id.toString())
            withContext(Dispatchers.IO) {
                try {
                    movieDao.insertMovie(movie)
                    movieDetails.data?.let { movieDao.insertMovieDetails(it) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            _movieDetails.emit(Events(movieDetails))
        } finally {
            _isLoading.emit(false)
        }
    }

    fun getMoviesDetailsFromDataBase(movie: Movie): MovieDetails {
        return movieRepository.getMoviesDetailsFromDataBase(movie.id)
    }

    fun observeConnectivity(context: Context) {
        viewModelScope.launch {
            movieRepository.observeConnectivity(context)
                .collect { status ->
                    _internetStatus.value = status
                    setIsInternetAvailable(status == ConnectivityObserver.Status.Available)
                }
        }
    }

}