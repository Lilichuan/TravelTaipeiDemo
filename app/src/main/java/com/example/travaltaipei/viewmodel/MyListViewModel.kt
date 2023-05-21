package com.example.travaltaipei.viewmodel


import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travaltaipei.network.TravelTaipeiApi
import com.example.travaltaipei.network.beans.AllPageData
import com.example.travaltaipei.network.beans.ListItemData
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MyListViewModel : ViewModel(){

    init {

    }

    var listData : MutableLiveData<List<ListItemData>> = MutableLiveData<List<ListItemData>>()
    var addableListData = ArrayList<ListItemData>()

    var retrofit = Retrofit.Builder()
        .baseUrl("https://www.travel.taipei/open-api/")
        .build()

    lateinit var selectData : ListItemData

    fun getMainList(lang : String){
        showLog("parameter lang is:$lang")
        viewModelScope.launch {
            try {
                val api = retrofit.create(TravelTaipeiApi::class.java)
                val response = api.getMainPageList(lang).execute()
                if (response.isSuccessful){
                    showLog("api getMainPageList() success")
                    response.body()?.data?.let {
                        addableListData.addAll(it)
                        listData.value = addableListData
                    }
                } else{
                    showLog("api getMainPageList() not success")
                }
            }catch (exception : Exception){
                exception.printStackTrace()
                showLog("MyListViewModel.getMainList() have Exception")
                listData.value = addableListData
            }
        }

    }
}

fun showLog(message: String){
    Log.d("tim_debug", message)
}