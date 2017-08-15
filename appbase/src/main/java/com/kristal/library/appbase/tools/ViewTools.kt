package com.kristal.library.appbase.tools

import android.graphics.Rect
import android.graphics.RectF
import android.view.View

object ViewTools {
  fun getGlobalVisibleRect(pView: View): Rect {
    val rect = Rect()
    pView.getGlobalVisibleRect(rect)
    return rect
  }
  
  fun getParent(pView: View, pParent: Int): View {
    var pView = pView
    for (i in 0 until pParent) {
      val lViewParent = pView.parent ?: return pView
      pView = lViewParent as View
    }
    return pView
  }
  
  fun getRectFByOtherView(pOutRectF: RectF, pView: View, pOtherView: View) {
    val lPositionOther = IntArray(2)
    pOtherView.getLocationOnScreen(lPositionOther)
    
    val lPositionView = IntArray(2)
    pView.getLocationOnScreen(lPositionView)
    
    val lX = (lPositionView[0] - lPositionOther[0]) / pOtherView.width.toFloat()
    val lY = (lPositionView[1] - lPositionOther[1]) / pOtherView.height.toFloat()
    
    val lWidth = pView.width / pOtherView.width.toFloat()
    val lHeight = pView.height / pOtherView.height.toFloat()
    
    pOutRectF.set(lX, lY, lX + lWidth, lY + lHeight)
  }
}
