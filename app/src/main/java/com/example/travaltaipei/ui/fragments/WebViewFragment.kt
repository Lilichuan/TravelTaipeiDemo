package com.example.travaltaipei.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.travaltaipei.databinding.FragmentWebViewBinding

const val ARG_WEB_VIEW_URL = "param1"


class WebViewFragment : Fragment() {

    private var url: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_WEB_VIEW_URL)
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

    private fun initWebView(webView: WebView) {
        val webSetting = webView.settings
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE
        webSetting.setSupportZoom(true)
        webSetting.javaScriptEnabled = false
        url?.let { webView.loadUrl(it) }
    }

}