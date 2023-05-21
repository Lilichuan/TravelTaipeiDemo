package com.example.travaltaipei.ui.fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.travaltaipei.databinding.MainListItemBinding
import com.example.travaltaipei.network.beans.ListItemData
import com.example.travaltaipei.viewmodel.showLog

class MainListViewHolder(itemView: View, viewBinding : MainListItemBinding) : ViewHolder(itemView) {

    val _binding = viewBinding

    fun setData(data: ListItemData) {
        _binding.titleText.text = data.name
        _binding.root.tag = data
        if(data.images == null){
            showLog("MainListViewHolder:")
            return
        }

        if(data.images!!.isEmpty()){
            showLog("MainListViewHolder: data.images is empty")
            return
        }
        data.images?.get(0)?.src?.let {
            showLog("MainListViewHolder:start _binding.imageView.load(it)")
            _binding.imageView.load(it)
        }
    }
}
