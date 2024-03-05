package com.noman.movie.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("load")
fun loadImage(view: ImageView, url: String?) {
    if (url != null) {
        Glide.with(view).load(Constants.IMAGE_BASE_URL + url).into(view)
    } else {
        // Handle null URL, such as logging an error or providing a default image
    }
}