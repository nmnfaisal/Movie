package com.noman.movie.ui.screens.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.noman.movie.data.remote.client.MovieService
import com.noman.movie.data.remote.dto.MovieDetails
import com.noman.movie.data.repository.MovieDetailsRepository
import com.noman.movie.utils.Events
import com.noman.movie.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel

@Inject constructor(
    private val movieService: MovieService,
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private val query = MutableLiveData<String>("")
    private val _movieDetails = MutableLiveData<Events<com.noman.movie.utils.Result<MovieDetails>>>()
    val movieDetails: LiveData<Events<com.noman.movie.utils.Result<MovieDetails>>> = _movieDetails

    val list = query.switchMap {searchQuery->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(searchQuery, movieService)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(s: String) {
        query.postValue(s)
    }

    fun getMovieDetails(movieID: String) = viewModelScope.launch {
        _movieDetails.postValue(Events(com.noman.movie.utils.Result(Status.LOADING, null, null)))
        _movieDetails.postValue(Events(repository.getMovieDetails(movieID)))
    }
}