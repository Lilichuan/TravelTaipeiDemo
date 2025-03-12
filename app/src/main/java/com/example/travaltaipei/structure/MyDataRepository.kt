package com.example.travaltaipei.structure

import androidx.lifecycle.MutableLiveData
import com.example.travaltaipei.network.beans.ListItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class MyDataRepository @Inject constructor() {
    private val addableListData = ArrayList<ListItemData>()
    //private val _listData = MutableStateFlow<List<ListItemData>>(addableListData)
    var listData: MutableLiveData<List<ListItemData>> = MutableLiveData<List<ListItemData>>()

    var total = 0

    var selectData: ListItemData? = null

    fun addData(receive : List<ListItemData>){
        addableListData.addAll(receive)
//        _listData.update {
//            return@update ArrayList(addableListData)
//        }

        listData.postValue(addableListData)
    }

    fun countPage(): Int {
        if (addableListData.isEmpty()) return 1
        if (addableListData.size > 0 && total > 0 && addableListData.size == total) {
            return -1
        }
        return (addableListData.size / 30) + 1
    }
}