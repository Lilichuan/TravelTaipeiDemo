package com.example.travaltaipei.ui.fragments

import com.example.travaltaipei.network.beans.ListItemData

interface AdapterCallback {
    fun onLoadMore()
    fun onItemSelect(item : ListItemData)
}