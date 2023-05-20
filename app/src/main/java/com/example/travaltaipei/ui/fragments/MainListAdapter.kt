package com.example.travaltaipei.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.travaltaipei.databinding.MainListItemBinding
import com.example.travaltaipei.network.beans.ListItemData

class MainListAdapter : Adapter<MainListViewHolder>() {

    var dataList: List<ListItemData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val viewBind = MainListItemBinding.inflate(LayoutInflater.from(parent.context))
        val viewHolder = MainListViewHolder(viewBind.root, viewBind)
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
        val data = dataList?.get(position)
        data?.let {
            holder.setData(it)
        }
    }

}