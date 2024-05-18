package com.example.travaltaipei.network

import android.util.Log
import com.example.travaltaipei.network.beans.AllPageData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyNetManager(val gson: Gson) {

    private var okHttpClient: OkHttpClient
    private val retrofit: Retrofit

    private val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            showLog(message)
        }
    })

    init {
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient().newBuilder().addInterceptor(logging).build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/open-api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    suspend fun getDataByPageNum(lang: String, pageNum : Int) : Response<AllPageData> {
        val api = retrofit.create(TravelTaipeiApi::class.java)
        return api.getMainPageList(lang, pageNum).execute()
    }

    fun showLog(message: String) {
        Log.d("tim_debug", message)
    }
}