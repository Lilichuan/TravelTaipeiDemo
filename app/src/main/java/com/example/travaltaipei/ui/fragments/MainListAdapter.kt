package com.example.travaltaipei.ui.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.travaltaipei.databinding.MainListItemBinding
import com.example.travaltaipei.network.beans.ListItemData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class MainListAdapter @Inject constructor(
    @ActivityContext var context: Context,
    val gson: Gson,
) : Adapter<MainListViewHolder>() {

    var dataList: List<ListItemData>? = null
    private var screenW: Int = 0
    private var lang: String = ""

    fun initScreen(screenWidth: Int, language: String) {
        screenW = screenWidth
        lang = language
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val viewBind = MainListItemBinding.inflate(LayoutInflater.from(parent.context))
        val viewHolder = MainListViewHolder(viewBind.root, viewBind, screenW)
        if (screenW > 0) {
            viewBind.root.minWidth = screenW
            viewBind.root.maxWidth = screenW
            viewBind.imageView.maxWidth = screenW
            viewBind.imageView.minimumWidth = screenW
        }

        viewBind.root.setOnClickListener {
            val data = it.tag as ListItemData
            adapterCallback?.onItemSelect(data)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        if (dataList == null) {
            return 0
        } else {
            return dataList!!.size
        }
    }


    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        dataList?.get(position)?.let {
            holder.setData(it)
        }

        val dataCount = getItemCount()
        if (dataCount > 0 && position == dataCount - 1) {
            adapterCallback?.onLoadMore()
        }

    }

    var adapterCallback: AdapterCallback? = null

}