package com.kristal.library.appbase.grafik.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.os.Build

/**
 * Created by Kristal on 26/02/2017.
 */

class CanvasLine(context: Context?) : BaseCanvas<CanvasLine>(context) {
  private var isRound: Boolean = false
  private var rectF: RectF? = null
  
  override fun _onBuild() {}
  
  fun round(): CanvasLine {
    isDirty = true
    isRound = true
    return this
  }
  
  fun drawVertical(pX: Float, pY: Float, pHeight: Float, canvas: Canvas) {
    build()
    
    if (stroke > 0 && isRound) {
      draw(pX, pY + stroke / 2, pX + 1, pY + pHeight - stroke / 2, canvas)
    } else {
      draw(pX, pY, pX, pY + pHeight, canvas)
    }
  }
  
  fun drawHorizontal(pX: Float, pY: Float, pWidth: Float, canvas: Canvas) {
    build()
    
    if (stroke > 0 && isRound) {
      draw(pX + stroke / 2, pY, pX + pWidth - stroke / 2, pY + 1, canvas)
    } else {
      draw(pX, pY, pX + pWidth, pY, canvas)
    }
  }
  
  fun draw(pLeft: Float, pTop: Float, pRight: Float, pBottom: Float, canvas: Canvas) {
    build()
    
    if (!isRound) {
      canvas.drawLine(pLeft, pTop, pRight, pBottom, paint!!)
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        canvas.drawRoundRect(pLeft, pTop, pRight, pBottom,
            stroke,
            stroke,
            paint!!)
      } else {
        rectF = RectF(pLeft, pTop, pRight, pBottom)
        canvas.drawRoundRect(rectF!!, stroke, stroke, paint!!)
      }
    }
  }
}
