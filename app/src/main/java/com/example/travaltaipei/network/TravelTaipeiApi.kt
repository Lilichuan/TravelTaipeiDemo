package com.example.travaltaipei.network

import com.example.travaltaipei.network.beans.AllPageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

public interface TravelTaipeiApi {
    @Headers("Accept: application/json")
    @GET("{lang}/Attractions/All")
    fun getMainPageList(@Path("lang")  language : String , @Query("page") page: Int) : Call<AllPageData>

}