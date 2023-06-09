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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.FragmentMainListBinding
import com.example.travaltaipei.viewmodel.MyListViewModel
import com.example.travaltaipei.viewmodel.showLog


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
    var screenW = 0
    var screenH = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainListBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(MyListViewModel::class.java)

        viewModel.listData.observe(this, Observer {
            if(viewModel.addableListData.isEmpty()){
                findNavController().navigate(R.id.action_main_list_to_net_error)
                //                val snackbar = Snackbar.make(binding.root, R.string.net_error, Snackbar.LENGTH_LONG)
//                    .setAction(R.string.try_again){
//                        startGetDataFromNet()
//                    }
//                snackbar.show()
            }else{
                adapter.dataList = it
                adapter.notifyDataSetChanged()
            }
        })


    }

    override fun onStart() {
        super.onStart()
        initScreenWH()
        initRecyclerView()
        startGetDataFromNet()
    }

    private fun initRecyclerView() {
        adapter = MainListAdapter(context ,viewModel, screenW)
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

    private fun startGetDataFromNet(){
        val param = getString(R.string.country_lang)
        viewModel.getMainList(param)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainListFragment()
    }

}