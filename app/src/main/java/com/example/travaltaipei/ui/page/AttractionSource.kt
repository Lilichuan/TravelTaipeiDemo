package com.example.travaltaipei.ui.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.travaltaipei.network.MyNetManager
import com.example.travaltaipei.network.beans.ListItemData
import com.example.travaltaipei.viewmodel.showLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AttractionSource(val lang : String, val myNetManager: MyNetManager):
    PagingSource<Int, ListItemData>() {

    var addableListData = ArrayList<ListItemData>()
    var total = -1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItemData> {
        showLog("start myNetManager.getDataByPageNum(lang , countPage())")
        withContext(Dispatchers.IO){
            val response = myNetManager.getDataByPageNum(lang , countPage())

            if (response.isSuccessful) {
                showLog("api getMainPageList() success")
                response.body()?.let {
                    total = it.total
                }

                response.body()?.data?.let {
                    addableListData.addAll(it)
                    return LoadResult.Page(it, it[0].id, it.get(it.size - 1).id)
                }

            }
            return LoadResult.Page(emptyList(), null, null)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ListItemData>): Int? {
        val lastItem = state.lastItemOrNull() ?: return null
        return lastItem.id
    }

    private fun countPage(): Int {
        if (addableListData.isEmpty()) return 1
        if (addableListData.size > 0 && total > 0 && addableListData.size == total) {
            return -1
        }
        return (addableListData.size / 30) + 1
    }
}