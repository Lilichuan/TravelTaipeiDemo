package com.example.travaltaipei.ui.fragments.detailpage

import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.example.travaltaipei.R
import com.example.travaltaipei.network.beans.MainListItemDataImage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DetailPictureAdapter(val list : List<MainListItemDataImage>) : Adapter<DetailPictureViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPictureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_image_item, parent, false)
        return DetailPictureViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DetailPictureViewHolder, position: Int) {
        val urlStr = list.get(position).src
        holder.resetPicture()
        if(!TextUtils.isEmpty(urlStr)){
            val uri = createUri(urlStr)
            //holder.imageView.load(uri)
            holder.picUrl = urlStr
            Picasso.with(holder.imageView.context).load(uri).into(holder.imageView, PicassoCallback(urlStr!!, holder))
        }
    }


    private class PicassoCallback(val processUrl : String, val holder: DetailPictureViewHolder,) : Callback{
        override fun onSuccess() {
            if(!holder.isSameUrl(processUrl)){
                holder.resetPicture()
            }
        }

        override fun onError() {

        }
    }
}



fun createUri(str : String?) : Uri {
    str?.let {
        return str.toUri().buildUpon().scheme("https").build()
    }
    return Uri.EMPTY
}