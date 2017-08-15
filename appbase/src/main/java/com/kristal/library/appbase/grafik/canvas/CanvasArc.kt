package com.kristal.library.appbase.grafik.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.support.v4.content.ContextCompat
import com.kristal.library.appbase.tools.PaintTools

/**
 * Created by Kristal on 26/02/2017.
 */

class CanvasArc(context: Context?) : BaseCanvas<CanvasArc>(context) {
  private var colorBg: Int = 0
  private var rectF: RectF? = null
  private var paintBg: Paint? = null
  
  fun colorBg(pColor: Int): CanvasArc {
    isDirty = true
    colorBg = pColor
    return this
  }
  
  override fun _onBuild() {
    if (colorBg != 0) {
      colorBg = when (context) {
        null -> colorBg
        else -> ContextCompat.getColor(context, colorBg)
      }
      
      paintBg = when {
        stroke <= 0 -> PaintTools.basic(colorBg)
        else -> PaintTools.strokeOnly(colorBg, stroke)
      }
    }
  }
  
  fun draw(pCenterX: Float, pCenterY: Float, pRadius: Float, pStartAngle: Float, pSweepAngle: Float, pCanvas: Canvas) {
    build()
    
    var pRadius = pRadius
    if (stroke > 0) {
      pRadius -= stroke
    }
    
    if (paintBg != null)
      pCanvas.drawCircle(pCenterX, pCenterY, pRadius, paintBg)
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      pCanvas.drawArc(pCenterX - pRadius,
          pCenterY - pRadius,
          pCenterX + pRadius,
          pCenterY + pRadius,
          pStartAngle,
          pSweepAngle,
          stroke <= 0, paint!!)
    } else {
      rectF = RectF(pCenterX - pRadius,
          pCenterY - pRadius,
          pCenterX + pRadius,
          pCenterY + pRadius)
      pCanvas.drawArc(rectF!!, pStartAngle, pSweepAngle, stroke <= 0, paint!!)
    }
  }
  
  fun draw(pCenterX: Float, pCenterY: Float, pRadius: Float, pProgress: Float, pCanvas: Canvas) {
    build()
    draw(pCenterX, pCenterY, pRadius, -90f, 360 * pProgress, pCanvas)
  }
}
