package com.example.travaltaipei.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.travaltaipei.network.beans.ListItemData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


private const val ITEMS_PER_PAGE = 30
class MyListViewModel() : ViewModel() {

    private val repository = AttractionRepository()

    fun getGson() = repository.gson

    fun queryItems(lang : String) : Flow<PagingData<ListItemData>>{
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { repository.attractionSource(lang) }
        ).flow.cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }
}

fun showLog(message: String) {
    Log.d("tim_debug", message)
}