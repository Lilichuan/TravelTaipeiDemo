package com.example.travaltaipei.ui.fragments.detailpage

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.compose.ui.unit.TextUnit
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.travaltaipei.R

class DetailPictureViewHolder(itemView: View) : ViewHolder(itemView) {
    var imageView :ImageView
    var picUrl : String? = null

    init {
        imageView = itemView.findViewById(R.id.image_view)
    }

    fun resetPicture(){
        imageView.setImageResource(android.R.color.transparent)
        picUrl = null
    }

    fun isSameUrl(another : String) : Boolean{
        if(TextUtils.isEmpty(picUrl)){
            return false
        }
        return picUrl.contentEquals(another)
    }
}