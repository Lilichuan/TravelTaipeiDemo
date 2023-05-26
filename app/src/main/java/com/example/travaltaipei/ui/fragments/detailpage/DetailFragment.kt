package com.example.travaltaipei.ui.fragments.detailpage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.FragmentDetailBinding
import com.example.travaltaipei.network.beans.ListItemData
import com.example.travaltaipei.ui.fragments.ARG_WEB_VIEW_URL
import com.example.travaltaipei.viewmodel.MyListViewModel


const val SINGLE_DATA_KEY = "single_data_key"

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

        initButton()
        initText()
        return viewBind.root
    }

    override fun onStart() {
        super.onStart()
        initPictureRecyclerView()
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
        val viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(MyListViewModel::class.java)
        val str = arguments?.getString(SINGLE_DATA_KEY)
        str?.let {
            data = viewModel.gson.fromJson(str, ListItemData::class.java)
        }
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
                data?.official_site?.let {
                    if(TextUtils.isEmpty(it)){
                        return@let
                    }
                    //openWebPage(it)
                    toWebView(it)
                }
            }
        }

    }

    fun toWebView(url: String){
        val bundle = bundleOf(ARG_WEB_VIEW_URL to url )
        findNavController().navigate(R.id.action_detail_page_to_web_site,bundle)
    }

    fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        activity?.let {
            if (intent.resolveActivity(it.packageManager) != null) {
                startActivity(intent)
            }
        }

    }


}