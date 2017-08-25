package com.kristal.library.mylibrary

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kristal.library.appbase.extensions.className
import com.kristal.library.appbase.fragment.BaseFragment
import com.kristal.library.appbase.recyclerview.adapter.RecyclerViewAdapter
import com.kristal.library.appbase.recyclerview.data.StringData
import com.kristal.library.mylibrary.databinding.MenuHolderBinding
import com.kristal.library.mylibrary.example.lifecycleactivity.LifecycleActivityA
import kotlinx.android.synthetic.main.menu_fragment.*

/**
 * Created by Dwi on 8/15/2017.
 */

class MenuFragment : BaseFragment() {
  override fun onSetup() = Setup(TAG, R.layout.menu_fragment)
  
  private val LFActivity = "Life Cycle Activity"
  
  val data = listOf(StringData(LFActivity), StringData(""), StringData(""), StringData(""))
  
  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupRecycleView()
  }
  
  private fun setupRecycleView() {
    RecyclerViewAdapter(data).apply {
      register<MenuHolderBinding, StringData>(R.layout.menu_holder) {
        it.onBind { holder, data ->
          holder.binding.btn.text = data.string
        }
        it.onClick {
          when (it.binding.btn.text) {
            LFActivity -> startActivity(Intent(context, LifecycleActivityA::class.java))
          }
        }
      }
      apply(recycler_view)
    }
  }
  
  companion object {
    var TAG = MenuFragment.className
  }
}
