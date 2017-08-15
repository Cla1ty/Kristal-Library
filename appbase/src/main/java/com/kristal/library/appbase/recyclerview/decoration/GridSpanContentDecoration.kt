package com.kristal.library.appbase.recyclerview.decoration

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Kristal on 2/15/2017.
 */

/**
 *  ____          ____
 * |    |        |    |
 * |____| <span> |____|
 *
 * <span>        <span>
 *  ____          ____
 * |    |        |    |
 * |____| <span> |____|
 */

open class GridSpanContentDecoration(pManager: GridLayoutManager, pSpanSize: Float) : RecyclerView.ItemDecoration() {
  protected var spanCount: Int = pManager.spanCount
  
  private val spanSize25: Int = (pSpanSize * 0.25f).toInt()
  private val spanSize50: Int = (pSpanSize * 0.5f).toInt()
  private val spanSize75: Int = (pSpanSize * 0.75f).toInt()
  
  private val spanSize33: Int = (pSpanSize * 0.33f).toInt()
  private val spanSize66: Int = (pSpanSize * 0.66f).toInt()
  
  protected var spanSize: Int = pSpanSize.toInt()
  
  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
    when (spanCount) {
      2 -> spanTwo(outRect, getPosition(parent, view))
      3 -> spanThree(outRect, getPosition(parent, view))
      4 -> spanFour(outRect, getPosition(parent, view))
    }
    
    outRect.top = 0
    outRect.bottom = spanSize
  }
  
  private fun getPosition(pRecyclerView: RecyclerView, pView: View): Int {
    return pRecyclerView.getChildLayoutPosition(pView) % spanCount
  }
  
  private fun spanTwo(outRect: Rect, pPosition: Int) {
    when (pPosition) {
      0 -> {
        outRect.left = 0
        outRect.right = spanSize50
      }
      1 -> {
        outRect.left = spanSize50
        outRect.right = 0
      }
    }
  }
  
  private fun spanThree(pOutRect: Rect, pPosition: Int) {
    when (pPosition) {
      0 -> {
        pOutRect.left = 0
        pOutRect.right = spanSize66
      }
      1 -> {
        pOutRect.left = spanSize33
        pOutRect.right = spanSize33
      }
      2 -> {
        pOutRect.left = spanSize66
        pOutRect.right = 0
      }
    }
  }
  
  private fun spanFour(pOutRect: Rect, pPosition: Int) {
    when (pPosition) {
      0 -> {
        pOutRect.left = 0
        pOutRect.right = spanSize75
      }
      1 -> {
        pOutRect.left = spanSize25
        pOutRect.right = spanSize50
      }
      2 -> {
        pOutRect.left = spanSize50
        pOutRect.right = spanSize25
      }
      3 -> {
        pOutRect.left = spanSize75
        pOutRect.right = 0
      }
    }
  }
}
