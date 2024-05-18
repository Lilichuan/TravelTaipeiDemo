package com.example.travaltaipei.viewmodel

import com.example.travaltaipei.network.MyNetManager
import com.example.travaltaipei.ui.page.AttractionSource
import com.google.gson.Gson

class AttractionRepository() {

    val gson = Gson()
    fun attractionSource(lang : String) = AttractionSource(lang, MyNetManager(gson))
}