package com.example.travaltaipei.ui.fragments.detailpage

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.FragmentDetailBinding
import com.example.travaltaipei.network.beans.ListItemData
import com.example.travaltaipei.viewmodel.MyListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    lateinit var viewBind: FragmentDetailBinding
    var data: ListItemData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBind = FragmentDetailBinding.inflate(inflater)
        initDataFromViewModel()
        initPictureRecyclerView()
        initButton()
        initText()
        return viewBind.root
    }

    private fun initText (){
        data?.name?.let {
            viewBind.name.text = it
        }

        data?.introduction?.let {
            viewBind.intro.text = it
        }

        data?.remind?.let {
            viewBind.remind.text = it
        }
    }

    private fun initDataFromViewModel() {
        val viewModel = ViewModelProvider(this).get(MyListViewModel::class.java)
        data = viewModel.selectData
    }

    private fun initPictureRecyclerView() {

        LinearLayoutManager(context).let {
            it.orientation = LinearLayoutManager.HORIZONTAL
            viewBind.picRecyclerView.layoutManager= it

        }

        data?.images?.let {
            viewBind.picRecyclerView.adapter = DetailPictureAdapter(it)
        }
    }

    private fun initButton() {

        if (TextUtils.isEmpty(data?.official_site)) {
            viewBind.toWebsite.visibility = View.GONE
        } else {
            viewBind.toWebsite.setOnClickListener {
                //TODO start webview Here

            }
        }

    }

}