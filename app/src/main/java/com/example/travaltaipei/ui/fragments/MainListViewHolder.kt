package com.example.travaltaipei.ui.fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.travaltaipei.databinding.MainListItemBinding
import com.example.travaltaipei.network.beans.ListItemData
import com.example.travaltaipei.ui.fragments.detailpage.createUri
import com.example.travaltaipei.viewmodel.showLog
import com.squareup.picasso.Picasso

class MainListViewHolder(itemView: View, viewBinding: MainListItemBinding, val screenW: Int) :
    ViewHolder(itemView) {

    val _binding = viewBinding


    fun setData(data: ListItemData) {
        _binding.titleText.text = data.name
        _binding.root.tag = data
        if (data.images == null) {
            showLog("MainListViewHolder:")
            return
        }

        if (data.images!!.isEmpty()) {
            showLog("MainListViewHolder: data.images is empty")
            return
        }
        data.images?.get(0)?.let {
            val uri = createUri(it.src)
            showLog("MainListViewHolder: start ${it.src}")
            //_binding.imageView.load(uri)
            Picasso.with(_binding.imageView.context).load(uri).into(_binding.imageView)
        }
    }

}
