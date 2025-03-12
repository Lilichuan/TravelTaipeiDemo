package com.example.travaltaipei.ui.fragments

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.travaltaipei.R
import com.example.travaltaipei.databinding.FragmentInternetErrorBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InternetErrorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class InternetErrorFragment : Fragment() {

    lateinit var viewBinder : FragmentInternetErrorBinding
    var connectivityManager : ConnectivityManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager = getSystemService(requireContext(), ConnectivityManager::class.java)
        connectivityManager?.addDefaultNetworkActiveListener {
            gotoMainPage()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinder = FragmentInternetErrorBinding.inflate(inflater)
        viewBinder.tryAgainBtn.setOnClickListener {
            gotoMainPage()
        }

        return viewBinder.root
    }

    private fun gotoMainPage() {
        viewLifecycleOwner.lifecycleScope.launch{
            findNavController().navigate(R.id.action_net_error_to_main_list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager?.removeDefaultNetworkActiveListener {
            gotoMainPage()
        }
    }

}