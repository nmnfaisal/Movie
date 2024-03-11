package com.noman.movie.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.remote.dto.MovieDetails

@BindingAdapter("load")
fun loadImage(view: ImageView, movie: Movie?) {
    movie?.let {
        movie.getPosterBitmap()?.let { bitmap ->
            view.setImageBitmap(bitmap)
        } ?: run {
            Glide.with(view).load(Constants.IMAGE_BASE_URL + movie.poster_path).into(view)
        }
    }
}

@BindingAdapter("loadDetailImage")
fun loadDetailImage(view: ImageView, movieDetails: MovieDetails?) {
    movieDetails?.let {
        movieDetails.getPosterBitmap()?.let { bitmap ->
            view.setImageBitmap(bitmap)
        } ?: run {
            Glide.with(view).load(Constants.IMAGE_BASE_URL + movieDetails.poster_path).into(view)
        }
    }
}