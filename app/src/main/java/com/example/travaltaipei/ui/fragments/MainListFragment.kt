package com.example.travaltaipei.ui.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.FragmentMainListBinding
import com.example.travaltaipei.viewmodel.MyListViewModel
import com.example.travaltaipei.viewmodel.showLog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainListFragment : Fragment() {

    private lateinit var binding: FragmentMainListBinding
    private val viewModel : MyListViewModel by viewModels()
    private lateinit var adapter : MainListAdapter
    private var screenW = 0
    private var screenH = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainListBinding.inflate(inflater)
        initScreenWH()
        initRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                val items = viewModel.queryItems(getString(R.string.country_lang))
                items.collectLatest {
                    adapter.submitData(it)
                }
            }

        }
        return binding.root
    }



    private fun initRecyclerView() {
        adapter = MainListAdapter(context ,viewModel.getGson(), screenW)
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



}