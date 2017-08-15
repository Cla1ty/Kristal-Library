package com.kristal.library.appbase.recyclerview.layoutmanager

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView

/**
 * Created by Kristal on 3/15/2017.
 */

class CenterLinearLayoutManager : LinearLayoutManager {
  
  private var smoothScroller: RecyclerView.SmoothScroller? = null
  
  constructor(context: Context) : super(context) {
    init(context)
  }
  
  constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {
    init(context)
  }
  
  private fun init(pContext: Context) {
    smoothScroller = CenterSmoothScroller(pContext)
  }
  
  override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
    smoothScroller!!.targetPosition = position
    startSmoothScroll(smoothScroller)
  }
  
  private inner class CenterSmoothScroller internal constructor(context: Context) : LinearSmoothScroller(context) {
    
    override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
      return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
    }
  }
}
