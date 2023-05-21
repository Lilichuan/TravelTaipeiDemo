package com.example.travaltaipei.ui.fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.travaltaipei.databinding.MainListItemBinding
import com.example.travaltaipei.network.beans.ListItemData

class MainListViewHolder(itemView: View, viewBinding : MainListItemBinding) : ViewHolder(itemView) {

    val _binding = viewBinding

    fun setData(data: ListItemData) {
        _binding.titleText.text = data.name
        if(data.images == null){
            return
        }

        if(data.images!!.isEmpty()){
            return
        }
        data.images?.get(0)?.src?.let {
            _binding.imageView.load(it)
        }
    }
}
