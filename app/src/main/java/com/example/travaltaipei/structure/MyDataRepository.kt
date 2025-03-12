package com.example.travaltaipei.structure

import com.example.travaltaipei.network.beans.ListItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class MyDataRepository @Inject constructor() {
    var addableListData = ArrayList<ListItemData>()
    private val _listData = MutableStateFlow<List<ListItemData>>(addableListData)
    val listDataFlow : StateFlow<List<ListItemData>> = _listData
    var total = 0

    var selectData: ListItemData? = null

    fun addData(receive : List<ListItemData>){
        _listData.value = addableListData.also {
            it.addAll(receive)
        }
    }

    fun countPage(): Int {
        if (addableListData.isEmpty()) return 1
        if (addableListData.size > 0 && total > 0 && addableListData.size == total) {
            return -1
        }
        return (addableListData.size / 30) + 1
    }
}