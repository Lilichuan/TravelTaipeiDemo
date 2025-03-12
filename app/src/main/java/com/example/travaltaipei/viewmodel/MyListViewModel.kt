package com.example.travaltaipei.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travaltaipei.network.TravelTaipeiApi
import com.example.travaltaipei.structure.MyDataRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor (
    private val gson : Gson,
    val dataRepository: MyDataRepository,
    private val taipeiApi : TravelTaipeiApi
): ViewModel() {


    private val _mainListLoadingState = MutableStateFlow(true)
    val mainListLoadingState: StateFlow<Boolean> = _mainListLoadingState

    fun getMainList(lang: String) {
        if (mainListLoadingState.value) {
            return
        }
        showLog("parameter lang is:$lang")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val page = dataRepository.countPage()
                if (page < 1) {
                    return@withContext
                }

                _mainListLoadingState.value = true

                flow {
                    emit(taipeiApi.getMainPageList(lang, page).execute())
                }.filter {
                    if(!it.isSuccessful){
                        _mainListLoadingState.value = false
                    }
                    it.isSuccessful
                }.catch {
                    it.printStackTrace()
                    showLog("MyListViewModel.getMainList() have Exception")
                    _mainListLoadingState.value = false
                }.flowOn(Dispatchers.IO).collectLatest {
                    _mainListLoadingState.value = false
                    it.body()?.let {
                        dataRepository.total = it.total
                    }

                    it.body()?.data?.let {
                        dataRepository.addData(it)
                    }
                }
            }
        }
    }
}

fun showLog(message: String) {
    Log.d("tim_debug", message)
}