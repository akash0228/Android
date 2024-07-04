package com.example.tvapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.tvapp.data.ShowRowRepository
import kotlinx.coroutines.launch

class ShowRowViewModel(application: Application,val showRowRepo: ShowRowRepository) : AndroidViewModel(application) {
    private val _showRowList = MutableLiveData<List<ShowRow>>()
    var showRowList: LiveData<List<ShowRow>> =showRowRepo.showRowList

    private val _searchResults = MutableLiveData<List<ShowRow>>()
    val searchResults: LiveData<List<ShowRow>> = _searchResults

    init {
        viewModelScope.launch {
            showRowRepo.getAllshowRows()
        }
    }

    fun insert(showRow: ShowRow){
        viewModelScope.launch {
            showRowRepo.insertshowRow(showRow)
        }
    }

    fun update(showRow: ShowRow){
        viewModelScope.launch {
            showRowRepo.updateshowRow(showRow)
        }
    }

    fun delete(showRow: ShowRow){
        viewModelScope.launch {
            showRowRepo.deleteshowRow(showRow)
        }
    }

    fun getAllshowRows(): LiveData<List<ShowRow>> {
        return showRowList
    }

    //need to be filtered
//    fun getFavshowRows(): LiveData<List<ShowRow>> {
//        return showRowList.map { showRows-> showRows.filter { showRow -> showRow.isFavourite } }
//    }

}