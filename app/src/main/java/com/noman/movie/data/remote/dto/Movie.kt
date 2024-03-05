package com.noman.movie.data.remote.dto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.ByteArrayOutputStream

//@Entity(tableName = "MOVIES")
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
//    val genre_ids: List<Int>,
//    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
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