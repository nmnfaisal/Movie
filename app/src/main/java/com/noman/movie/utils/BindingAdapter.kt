package com.noman.movie.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.noman.movie.data.remote.dto.Movie
import com.noman.movie.data.remote.dto.MovieDetails


//@BindingAdapter("load")
//fun loadImage(view: ImageView, imageURL: String?) {
//    if (imageURL != null) {
//        Glide.with(view).load(Constants.IMAGE_BASE_URL + imageURL).into(view)
//    } else {
//
//    }
//}


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
fun loadDetailImage(view: ImageView, movie: MovieDetails?) {
    movie?.let {
        movie.getPosterBitmap()?.let { bitmap ->
            view.setImageBitmap(bitmap)
        } ?: run {
            Glide.with(view).load(Constants.IMAGE_BASE_URL + movie.poster_path).into(view)
        }
    }
}