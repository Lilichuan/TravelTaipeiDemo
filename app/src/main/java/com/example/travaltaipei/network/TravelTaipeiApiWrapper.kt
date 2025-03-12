package com.example.travaltaipei.network

import com.example.travaltaipei.network.beans.AllPageData
import retrofit2.Call
import javax.inject.Inject

class TravelTaipeiApiWrapper @Inject constructor() {

    var api : TravelTaipeiApi? = null

    fun getMainPageList(language: String, page: Int): Call<AllPageData> {
        return api!!.getMainPageList(language, page)
    }

}