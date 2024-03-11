package com.noman.movie.data.remote.dto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.ByteArrayOutputStream

data class MovieDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val homepage: String,
    @PrimaryKey
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var posterByteArray: ByteArray? = null
) {
    fun setPosterBitmap(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        posterByteArray = stream.toByteArray()
    }

    fun getPosterBitmap(): Bitmap? {
        return if (posterByteArray != null) {
            BitmapFactory.decodeByteArray(posterByteArray, 0, posterByteArray!!.size)
        } else {
            null
        }
    }
}