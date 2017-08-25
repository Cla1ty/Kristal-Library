package com.kristal.library.mylibrary

import android.os.Bundle

import com.kristal.library.appbase.activity.BaseActivity

class MainActivity : BaseActivity() {
  override fun onSetup() = Setup()
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    showFragment(MenuFragment())
  }
}
