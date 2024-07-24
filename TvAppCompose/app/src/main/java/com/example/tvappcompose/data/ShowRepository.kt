package com.example.tvapp.data

import android.util.Log
import androidx.room.Query
import com.example.tvappcompose.models.Show
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject


class ShowRepository @Inject constructor(private val showDatabase:ShowDatabase) {

    private val showDao=showDatabase.showDao()

    val allShows: Flow<List<Show>> = showDao.getAllShow()
    val recentShows:Flow<List<Show>> = showDao.getRecentShow()
    val watchLaterShows:Flow<List<Show>> = showDao.getWatchLaterShow()

    private val _show=MutableStateFlow<Show>(Show("","","",0,"","","",""))

    val show:StateFlow<Show>
        get() = _show

    suspend fun insertshow(show: Show){
        showDao.insert(show)
    }

    suspend fun updateshow(show: Show){
        showDao.update(show)
    }

    suspend fun deleteshow(show: Show){
        showDao.delete(show)
    }

    suspend fun getShow(id:Int){
       return _show.emit(showDao.getShow(id))
    }

   suspend fun searchShow(query: String):List<Show>{
        Log.d("MYAPP", "SearchShow: ${query}")
        return  showDao.getShows().filter { it.title.contains(query,ignoreCase = true) }
    }

}