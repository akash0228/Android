package com.example.videoplayer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_videos")
class Video(
    @ColumnInfo(name="title") var title:String,
    @ColumnInfo(name="url") var url:String,
    @ColumnInfo(name="lyrics") var lyrics:String,
    @ColumnInfo(name="isFavourite") var isFavourite:Boolean
){
    @PrimaryKey(autoGenerate = true) var id:Int=0
}
