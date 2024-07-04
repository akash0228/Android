package com.example.tvapp.data

import com.example.tvapp.ShowRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ShowRowRepository(private val showRowDatabase: ShowRowDatabase) {
    private val showRowDao=showRowDatabase.showRowDao()
    val showRowList=showRowDao.getAllShowRow()

    suspend fun insertshowRow(showRow: ShowRow){
        withContext(Dispatchers.IO){
            showRowDao.insert(showRow)
        }
    }

    suspend fun updateshowRow(showRow: ShowRow){
        withContext(Dispatchers.IO){
            showRowDao.update(showRow)
        }
    }

    suspend fun deleteshowRow(showRow: ShowRow){
        withContext(Dispatchers.IO){
            showRowDao.delete(showRow)
        }
    }

    suspend fun getAllshowRows(){
        withContext(Dispatchers.IO){
            showRowDao.getAllShowRow()
        }
    }
}