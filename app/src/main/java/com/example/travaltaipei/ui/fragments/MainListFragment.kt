package com.example.travaltaipei.ui.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.FragmentMainListBinding
import com.example.travaltaipei.network.beans.ListItemData
import com.example.travaltaipei.ui.fragments.detailpage.SINGLE_DATA_KEY
import com.example.travaltaipei.viewmodel.MyListViewModel
import com.example.travaltaipei.viewmodel.showLog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainListFragment : Fragment() {

    private lateinit var binding: FragmentMainListBinding
    @Inject lateinit var adapter : MainListAdapter
    @Inject lateinit var gson : Gson
    private var screenW = 0
    private var screenH = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel : MyListViewModel by hiltNavGraphViewModels(R.id.main_nav)
        binding = FragmentMainListBinding.inflate(inflater)

        val param = getString(R.string.country_lang)
        viewModel.getMainList(param)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                initScreenWH()
                initRecyclerView()

                viewModel.dataRepository.listDataFlow.collectLatest{
//                    if(it.isEmpty()){
//                        findNavController().navigate(R.id.action_main_list_to_net_error)
//                    }else{
//                        adapter.dataList = it
//                        adapter.notifyDataSetChanged()
//                    }
                    adapter.dataList = it
                    adapter.notifyDataSetChanged()
                }


            }
        }

        adapter.adapterCallback = object : AdapterCallback{
            override fun onLoadMore() {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getMainList(getString(R.string.country_lang))
                }
            }

            override fun onItemSelect(item: ListItemData) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.dataRepository.selectData = item
                    val str = gson.toJson(item)
                    val bundle = bundleOf(SINGLE_DATA_KEY to str)
                    findNavController().navigate(R.id.action_main_list_to_detail_page, bundle)
                }
            }
        }

        return binding.root
    }


    private fun initRecyclerView() {
        adapter.initScreen(screenW, getString(R.string.country_lang))
        LinearLayoutManager(context).let {
            it.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager= it
            binding.recyclerView.adapter = adapter
        }
    }

    private fun initScreenWH() {
        val wm = requireActivity().getApplication().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            screenW = windowMetrics.bounds.width()
            screenH = windowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            screenW = displayMetrics.widthPixels
            screenH = displayMetrics.heightPixels
        }
        showLog("screen W=$screenW and H=$screenH ")
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainListFragment()
    }

}