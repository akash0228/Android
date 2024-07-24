package com.example.tvapp

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.tvapp.data.ShowRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class ShowRowViewModel(application: Application) : AndroidViewModel(application) {
    private val showRepo= ShowRepository(application)

    private val _showList = MutableLiveData<List<Show>>()
    var showList: LiveData<List<Show>> =showRepo.showList

    private val _searchList = MutableLiveData<List<Show>>()
    var searchList: LiveData<List<Show>> =showList

    private val _showRowList = MutableLiveData<List<ShowRow>>()
    val showRowList: LiveData<List<ShowRow>> = _showRowList

    init {
        viewModelScope.launch {
            showRepo.getAllShow()
        }
    }

    fun refresh(){
        getRecentShows()
        getWatchListShows()
    }

    fun insert(show: Show){
        viewModelScope.launch {
            showRepo.insertshow(show)
        }
//        refresh()
    }

    fun update(show: Show){
        viewModelScope.launch {
            showRepo.updateshow(show)
        }
//        refresh()
    }

    fun delete(show: Show){
        viewModelScope.launch {
            showRepo.deleteshow(show)
        }
//        refresh()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getAllShowRow(): LiveData<List<ShowRow>> {
        val hashMap=HashMap<String,ArrayList<Show>>()
        showRepo.showList.value?.forEach { show ->
            val listShow: ArrayList<Show> = hashMap.getOrPut(show.Header) { ArrayList() }
            listShow.add(show)
        }

        val showRows= hashMap.entries.map { (header, listShow) ->
            ShowRow(header, listShow)
        }

        _showRowList.postValue(showRows)

        return showRowList
    }

    fun getRecentShows(): LiveData<List<Show>> {
        return showList.map { shows -> shows.filter { show -> show.isWatched } }
    }

    fun getWatchListShows():LiveData<List<Show>>{
        return showList.map { shows -> shows.filter { show -> show.inWatchList } }
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun searchShows(query: String) {
//        Log.d("TAG", "searchShows: ${showList.value?.size}")
//
//          searchList = showList.map { shows -> shows.filter { show -> show.title.contains(query,ignoreCase = true) } }
//        Log.d("TAG", "searchShows: $query ${searchList.value?.size}")
//
//    }


}