package com.example.travaltaipei.structure

import android.util.Log
import com.example.travaltaipei.network.TravelTaipeiApi
import com.example.travaltaipei.network.TravelTaipeiApiWrapper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object MyHiltModule {

    @Provides
    fun provideGson() : Gson{
        return Gson()
    }

    @Provides
    fun provideApi(gson: Gson) : TravelTaipeiApiWrapper {

        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("TimApp",message)
            }
        })

        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(logging).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/open-api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        val wrapper = TravelTaipeiApiWrapper()
        wrapper.api = retrofit.create(TravelTaipeiApi::class.java)
        return wrapper
    }

    @Provides
    fun provideDataRepository() : MyDataRepository{
        return MyDataRepository()
    }
}