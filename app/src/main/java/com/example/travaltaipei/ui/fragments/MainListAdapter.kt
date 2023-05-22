package com.example.travaltaipei.ui.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.MainListItemBinding
import com.example.travaltaipei.network.beans.ListItemData
import com.example.travaltaipei.ui.fragments.detailpage.SINGLE_DATA_KEY
import com.example.travaltaipei.viewmodel.MyListViewModel
import com.squareup.picasso.Picasso

class MainListAdapter(var context : Context?, var viewModel : MyListViewModel) : Adapter<MainListViewHolder>() {

    var dataList: List<ListItemData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val viewBind = MainListItemBinding.inflate(LayoutInflater.from(parent.context))
        val viewHolder = MainListViewHolder(viewBind.root, viewBind)
        viewBind.root.setOnClickListener {
            val data = it.tag as ListItemData
            val str = viewModel.gson.toJson(data)
            val bundle = bundleOf(SINGLE_DATA_KEY to str)
            viewModel.selectData = data
            it.findNavController().navigate(R.id.action_main_list_to_detail_page, bundle)
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
        val data = dataList?.get(position)
        data?.let {
            holder.setData(it)
        }
    }

    fun onDestroy(){
        //Picasso.with(context).shutdown()
    }

}