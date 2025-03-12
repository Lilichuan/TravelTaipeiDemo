package com.example.travaltaipei.structure

import android.util.Log
import com.example.travaltaipei.network.TravelTaipeiApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyHiltModule {

    @Provides
    @Singleton
    fun provideGson() : Gson{
        return Gson()
    }

    @Provides
    @Singleton
    fun provideTravelTaipeiApi(retrofit: Retrofit) : TravelTaipeiApi {
        return retrofit.create(TravelTaipeiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson) : Retrofit{
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("TimApp",message)
            }
        })

        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/open-api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    }

    @Provides
    fun provideDataRepository() : MyDataRepository{
        return MyDataRepository()
    }
}