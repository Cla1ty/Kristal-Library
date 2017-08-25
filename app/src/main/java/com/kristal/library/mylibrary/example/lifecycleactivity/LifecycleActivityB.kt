package com.kristal.library.mylibrary.example.lifecycleactivity

import com.kristal.library.appbase.activity.BaseActivity
import com.kristal.library.mylibrary.R

/**
 * Created by Dwi on 8/25/2017.
 */

class LifecycleActivityB : BaseActivity() {
  override fun onSetup() = Setup().apply {
    layoutId = R.layout.lifecycle_activity_b
  }
}
