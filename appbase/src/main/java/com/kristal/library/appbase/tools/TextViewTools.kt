package com.kristal.library.appbase.tools

import android.graphics.Paint
import android.graphics.Rect
import android.view.ViewTreeObserver
import android.widget.TextView

/**
 * Created by Dwi on 8/4/2017.
 */

object TextViewTools {
  fun setTextAutoResize(pView: TextView, pString: String) {
    pView.text = pString
    pView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
      override fun onPreDraw(): Boolean {
        pView.viewTreeObserver.removeOnPreDrawListener(this)
        
        val bounds = Rect()
        val textPaint = Paint(pView.paint)
        var lTextSize = pView.paint.textSize
        
        Trace.debug("Default Text Size: " + lTextSize)
        
        var width = 0
        var isFirst = true
        do {
          if (isFirst)
            isFirst = false
          else
            lTextSize--
          
          textPaint.textSize = lTextSize
          textPaint.getTextBounds(pString, 0, pString.length, bounds)
          
          width = bounds.width()
          Trace.debug("Default Width: " + pView.width + ", New Width: " + width)
        } while (pView.width < width)
        
        Trace.debug("New Text Size: " + lTextSize)
        val lNewTextSize = MathTools.pxToSp(lTextSize) - 2
        pView.textSize = lNewTextSize
        Trace.debug("New Text Size: " + pView.textSize)
        return true
      }
    })
  }
}
