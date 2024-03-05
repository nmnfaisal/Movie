package com.noman.movie.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class IntListConverter {
    @TypeConverter
    fun fromString(value: String): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Int>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}