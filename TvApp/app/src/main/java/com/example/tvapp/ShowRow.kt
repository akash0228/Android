package com.example.tvapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "show_row_table")
class ShowRow(@ColumnInfo(name="header")var header:String, @ColumnInfo(name="listshow") var listShow:List<Show>) {
    @ColumnInfo(name="isFavourite") var isFavourite:Boolean=false
    @PrimaryKey(autoGenerate = true) var id:Int=0
}