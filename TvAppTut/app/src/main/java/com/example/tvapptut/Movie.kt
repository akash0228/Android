package com.example.tvapptut

class Movie(val id:Long, val title:String,val studio:String) {
    override fun toString(): String {
        return "Movie(id=$id, title='$title', studio='$studio')"
    }
}