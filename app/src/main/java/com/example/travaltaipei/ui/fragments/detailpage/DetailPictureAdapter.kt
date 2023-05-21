package com.example.travaltaipei.ui.fragments.detailpage

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.example.travaltaipei.R
import com.example.travaltaipei.network.beans.MainListItemDataImage

class DetailPictureAdapter(val list : List<MainListItemDataImage>) : Adapter<DetailPictureViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPictureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_image_item, parent)
        return DetailPictureViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DetailPictureViewHolder, position: Int) {
        val data = list.get(position).src
        if(!TextUtils.isEmpty(data)){
            holder.imageView.load(data)
        }
    }

}