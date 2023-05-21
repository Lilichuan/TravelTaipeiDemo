package com.example.travaltaipei.ui.fragments.detailpage

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.travaltaipei.R

class DetailPictureViewHolder(itemView: View) : ViewHolder(itemView) {
    var imageView :ImageView

    init {
        imageView = itemView.findViewById(R.id.image_view)
    }
}