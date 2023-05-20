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

    var retrofit = Retrofit.Builder()
        .baseUrl("www.travel.taipei/open-api")
        .build()

    fun getMainList(lang : String){
        viewModelScope.launch {
            try {
                val api = retrofit.create(TravelTaipeiApi::class.java)
                val response = api.getMainPageList(lang).execute()
                if (response.isSuccessful){
                    showLog("api getMainPageList() success")
                    response.body()?.data?.let {
                        listData.value = it
                    }
                } else{
                    showLog("api getMainPageList() not success")
                }
            }catch (exception : Exception){
                exception.printStackTrace()
            }
        }

    }
}

fun showLog(message: String){
    Log.d("tim_debug", message)
}