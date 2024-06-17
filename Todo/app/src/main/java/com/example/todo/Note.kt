package com.example.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_notes")
class Note(
    @ColumnInfo(name="title") val title:String,
    @ColumnInfo(name="disp") val disp:String
) {
    @PrimaryKey(autoGenerate = true)var id:Int=0
}