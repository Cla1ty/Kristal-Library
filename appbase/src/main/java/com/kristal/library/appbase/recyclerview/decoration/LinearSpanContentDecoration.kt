package com.kristal.library.appbase.recyclerview.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Kristal on 07/03/2017.
 */

/**
 *  ______________________
 * |______________________|
 *
 *          <span>
 *  ______________________
 * |______________________|
 */

class LinearSpanContentDecoration(protected var spanSize: Float, private var edge: Boolean) : RecyclerView.ItemDecoration() {
  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
    val lIndex = parent.getChildLayoutPosition(view)
    
    outRect.left = if (edge) spanSize.toInt() else 0
    outRect.right = if (edge) spanSize.toInt() else 0
    
    outRect.top = if (edge && lIndex == 0) spanSize.toInt()
    else if (!edge && lIndex >= 1) spanSize.toInt() else 0
    outRect.bottom = if (edge) spanSize.toInt() else 0
  }
}
