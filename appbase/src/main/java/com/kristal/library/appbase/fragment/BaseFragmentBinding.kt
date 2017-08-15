package com.kristal.library.appbase.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kristal.library.appbase.tools.Trace

/**
 * Created by Kristal on 2/5/2017.
 */

abstract class BaseFragmentBinding<BINDING : ViewDataBinding> : BaseFragment() {
  
  lateinit var binding: BINDING
  
  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    Trace.debug("CREATE VIEW", this)
    
    binding = DataBindingUtil.inflate<BINDING>(inflater, setup.layoutId, container, false)
    return binding.root
  }
}