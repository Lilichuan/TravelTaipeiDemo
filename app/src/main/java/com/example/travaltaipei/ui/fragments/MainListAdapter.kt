package com.example.travaltaipei.ui.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.MainListItemBinding
import com.example.travaltaipei.network.beans.ListItemData
import com.example.travaltaipei.ui.fragments.detailpage.SINGLE_DATA_KEY
import com.google.gson.Gson

class MainListAdapter(
    var context: Context?, val gson : Gson, val screenW: Int
) :
    PagingDataAdapter<ListItemData,MainListViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val viewBind = MainListItemBinding.inflate(LayoutInflater.from(parent.context))
        val viewHolder = MainListViewHolder(viewBind.root, viewBind, screenW)
        if(screenW > 0){
            viewBind.root.minWidth = screenW
            viewBind.root.maxWidth = screenW
            viewBind.imageView.maxWidth = screenW
            viewBind.imageView.minimumWidth = screenW
        }

        viewBind.root.setOnClickListener {
            val data = it.tag as ListItemData
            val str = gson.toJson(data)
            val bundle = bundleOf(SINGLE_DATA_KEY to str)
            it.findNavController().navigate(R.id.action_main_list_to_detail_page, bundle)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.setData(it)
        }
    }

    private companion object {
        val itemCallback = object : DiffUtil.ItemCallback<ListItemData>() {
            override fun areItemsTheSame(oldItem: ListItemData, newItem: ListItemData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListItemData, newItem: ListItemData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}