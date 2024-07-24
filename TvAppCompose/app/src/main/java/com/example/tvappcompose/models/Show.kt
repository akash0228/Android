package com.example.tvappcompose.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "show_table")
class Show(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var Description: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "year") var year: Int,
    @ColumnInfo(name = "duration") var Duration: String,
    @ColumnInfo(name = "videoUrl") var videoUrl: String,
    @ColumnInfo(name = "imageUrl") var imageUrl: String,
    @ColumnInfo(name = "header") var Header: String
) {

    @ColumnInfo(name = "isWatchList")
    var inWatchList: Boolean = false

    @ColumnInfo(name = "isWatched")
    var isWatched: Boolean = false

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}