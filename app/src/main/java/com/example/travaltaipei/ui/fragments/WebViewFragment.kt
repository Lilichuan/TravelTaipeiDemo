package com.example.travaltaipei.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.travaltaipei.databinding.FragmentWebViewBinding

private const val ARG_URL = "param1"


class WebViewFragment : Fragment() {

    private var url: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentWebViewBinding.inflate(inflater)
        initWebView(bind.webview)
        return bind.root
    }

    private fun initWebView(webView: WebView){

    }

    companion object {

        @JvmStatic
        fun newInstance(url: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                }
            }
    }
}