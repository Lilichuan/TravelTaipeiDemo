package com.example.travaltaipei.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travaltaipei.network.TravelTaipeiApi
import com.example.travaltaipei.network.beans.ListItemData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyListViewModel : ViewModel() {

    private val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            //showLog(message)
        }
    })

    private var okHttpClient: OkHttpClient
    val retrofit: Retrofit
    val gson = Gson()

    init {

        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient().newBuilder().addInterceptor(logging).build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/open-api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    var listData: MutableLiveData<List<ListItemData>> = MutableLiveData<List<ListItemData>>()
    var addableListData = ArrayList<ListItemData>()
    var total = -1


    var selectData: ListItemData? = null

    fun getMainList(lang: String) {
        showLog("parameter lang is:$lang")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getMainListSus(lang)
            }
        }
    }

    private fun getMainListSus(lang: String) {
        val page = countPage()
        if (page < 1) {
            return
        }

        try {
            val api = retrofit.create(TravelTaipeiApi::class.java)
            val response = api.getMainPageList(lang, page).execute()
            if (response.isSuccessful) {
                showLog("api getMainPageList() success")
                response.body()?.let {
                    total = it.total
                }

                response.body()?.data?.let {
                    addableListData.addAll(it)
                    listData.postValue(addableListData)
                }

            } else {
                showLog("api getMainPageList() not success")
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            showLog("MyListViewModel.getMainList() have Exception")
            listData.postValue(addableListData)
        }
    }

    private fun countPage(): Int {
        if (addableListData.isEmpty()) return 1
        if (addableListData.size > 0 && total > 0 && addableListData.size == total) {
            return -1
        }
        return (addableListData.size / 30) + 1
    }


}

fun showLog(message: String) {
    Log.d("tim_debug", message)
}