package com.kristal.library.appbase.grafik.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.os.Build

/**
 * Created by Kristal on 26/02/2017.
 */

class CanvasRect(context: Context?) : BaseCanvas<CanvasRect>(context) {
  private var round: Int = 0
  private var rectF: RectF? = null
  
  override fun _onBuild() {}
  
  fun round(pRound: Int): CanvasRect {
    isDirty = true
    round = pRound
    return this
  }
  
  fun drawSquare(pX: Float, pY: Float, pWidth: Float, pHeight: Float, canvas: Canvas) {
    var pX = pX
    var pY = pY
    var pWidth = pWidth
    var pHeight = pHeight
    build()
    
    if (stroke > 0) {
      pX += stroke
      pY += stroke
      pWidth -= stroke / 2f
      pHeight -= stroke / 2f
    }
    
    var lRound = round
    if (round == -1) {
      lRound = Math.min(pWidth, pHeight).toInt() shr 1
    }
    
    if (lRound == 0) {
      canvas.drawRect(pX, pY, pX + pWidth, pY + pHeight, paint!!)
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        canvas.drawRoundRect(pX, pY, pX + pWidth, pY + pHeight, lRound.toFloat(), lRound.toFloat(), paint!!)
      } else {
        rectF = RectF(pX, pY, pX + pWidth, pY + pHeight)
        canvas.drawRoundRect(rectF!!, lRound.toFloat(), lRound.toFloat(), paint!!)
      }
    }
  }
}
