package com.kristal.library.mylibrary.example.lifecycleactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kristal.library.appbase.activity.BaseActivity
import com.kristal.library.mylibrary.R
import kotlinx.android.synthetic.main.lifecycle_activity_a.*

/**
 * Created by Dwi on 8/25/2017.
 */

class LifecycleActivityA : BaseActivity() {
  private val onClick = View.OnClickListener {
    startActivity(Intent(baseContext, LifecycleActivityB::class.java))
  }
  
  override fun onSetup() = Setup().apply {
    layoutId = R.layout.lifecycle_activity_a
  }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    btn_next.setOnClickListener(onClick)
  }
}

