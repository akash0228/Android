package com.example.tvapp

import androidx.room.TypeConverter
import com.google.gson.Gson


class ShowRowTypeConverter {

    @TypeConverter
    fun listToJson(value: List<Show>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Show>::class.java).toList()
}
