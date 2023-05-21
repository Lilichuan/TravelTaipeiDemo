package com.example.travaltaipei.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.FragmentMainListBinding
import com.example.travaltaipei.viewmodel.MyListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainListFragment : Fragment() {

    lateinit var binding: FragmentMainListBinding
    lateinit var viewModel: MyListViewModel
    lateinit var adapter : MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MainListAdapter()
        binding = FragmentMainListBinding.inflate(inflater)
        LinearLayoutManager(context).let {
            it.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager= it
            binding.recyclerView.adapter = adapter
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(MyListViewModel::class.java)
        viewModel.listData.observe(this, Observer {
            adapter.dataList = it
            adapter.notifyDataSetChanged()
        })
        val param = getString(R.string.country_lang)
        viewModel.getMainList(param)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainListFragment()
    }

}