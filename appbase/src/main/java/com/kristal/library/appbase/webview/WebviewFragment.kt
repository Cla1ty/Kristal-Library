package com.kristal.library.appbase.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kristal.library.appbase.R
import com.kristal.library.appbase.fragment.BaseFragment
import com.kristal.library.appbase.tools.Trace
import kotlinx.android.synthetic.main.web_view_fragment.*


/**
 * Created by Dwi on 8/14/2017.
 */

class WebviewFragment : BaseFragment() {
  
  override fun onSetup(): BaseFragment.Setup {
    return Setup(TAG, R.layout.web_view_fragment)
  }
  
  @SuppressLint("SetJavaScriptEnabled")
  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    progress_bar.max = 100
    
    if (arguments.containsKey(URL)) {
      Trace.info("Load URL: " + arguments.getString(URL)!!)
      web_view.setWebViewClient(WebViewClient())
      web_view.loadUrl(arguments.getString(URL))
      web_view.setWebChromeClient(object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
          progress_bar.progress = newProgress
          if (newProgress >= progress_bar.max) {
            progress_bar.visibility = View.GONE
          } else if (progress_bar.visibility == View.GONE) {
            progress_bar.visibility = View.VISIBLE
          }
        }
      })
      
      val webSettings = web_view.settings
      webSettings.javaScriptEnabled = true
      webSettings.domStorageEnabled = true
    }
  }
  
  override fun onBackPressed(): Boolean {
    if (web_view.canGoBack()) {
      web_view.goBack()
      return true
    }
    
    return super.onBackPressed()
  }
  
  companion object {
    val TAG = WebviewFragment::class.java.simpleName
    val URL = "url"
    
    fun newBundle(url: String): Bundle {
      val args = Bundle()
      args.putString(URL, url)
      return args
    }
  }
}
