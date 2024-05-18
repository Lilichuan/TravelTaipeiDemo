package com.example.travaltaipei.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travaltaipei.network.TravelTaipeiApi
import com.example.travaltaipei.network.beans.ListItemData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    private var isLoading = false

    fun getMainList(lang: String) {
        if (isLoading) {
            return
        }
        showLog("parameter lang is:$lang")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val page = countPage()
                if (page < 1) {
                    return@withContext
                }

                isLoading = true

                flow {
                    val api = retrofit.create(TravelTaipeiApi::class.java)
                    emit(api.getMainPageList(lang, page).execute())
                }.filter {
                    if(!it.isSuccessful){
                        isLoading = false
                    }
                    it.isSuccessful
                }.catch {
                    it.printStackTrace()
                    showLog("MyListViewModel.getMainList() have Exception")
                    isLoading = false
                    listData.postValue(addableListData)
                }.flowOn(Dispatchers.IO).collectLatest {
                    it.body()?.let {
                        total = it.total
                    }

                    it.body()?.data?.let {
                        addableListData.addAll(it)
                        listData.postValue(addableListData)
                    }

                    isLoading = false
                }
            }
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