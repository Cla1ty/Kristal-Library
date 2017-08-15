package com.kristal.library.appbase.grafik.canvas

import android.content.Context
import android.graphics.Canvas

import com.kristal.library.appbase.tools.PaintTools

/**
 * Created by Kristal on 26/02/2017.
 */

class CanvasText(context: Context?) : BaseCanvas<CanvasText>(context) {
  private var size: Int = 0
  
  fun size(pSize: Int): CanvasText {
    isDirty = true
    size = pSize
    return this
  }
  
  override fun _onBuild() {
    paint = PaintTools.text(color, size.toFloat())
  }
  
  fun draw(pText: String, pX: Float, pY: Float, pGravity: Gravity, pCanvas: Canvas) {
    build()
    
    val lX = (pX - pGravity.offsetX * paint!!.measureText(pText)).toInt()
    val lY = (pY - pGravity.offsetY * paint!!.textSize).toInt()
    pCanvas.drawText(pText, lX.toFloat(), lY.toFloat(), paint!!)
  }
  
  enum class Gravity private constructor(val offsetX: Float, val offsetY: Float) {
    LEFT(0f, 0.5f),
    TOP(0.5f, 0f),
    RIGHT(1f, 0.5f),
    BOTTOM(0.5f, 1f),
    CENTER(0.5f, 0.5f)
  }
}
