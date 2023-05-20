package com.example.travaltaipei.network

import com.example.travaltaipei.network.beans.AllPageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

public interface TravelTaipeiApi {

    @GET("{lang}/Attractions/All")
    fun getMainPageList(@Path("lang")  language : String ) : Call<AllPageData>

}