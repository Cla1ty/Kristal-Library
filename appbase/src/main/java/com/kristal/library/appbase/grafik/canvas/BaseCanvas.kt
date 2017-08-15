package com.kristal.library.appbase.grafik.canvas

import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat

import com.kristal.library.appbase.tools.PaintTools

/**
 * Created by Kristal on 26/02/2017.
 */

abstract class BaseCanvas<TYPE>(protected var context: Context?) {
  protected var color: Int = 0
  protected var stroke: Float = 0f
  protected var isDirty: Boolean = true
  protected var paint: Paint? = null
  
  protected abstract fun _onBuild()
  
  fun color(pColor: Int): TYPE {
    isDirty = true
    color = pColor
    return this as TYPE
  }
  
  fun stroke(pStroke: Float): TYPE {
    isDirty = true
    stroke = pStroke
    return this as TYPE
  }
  
  protected fun build() {
    if (!isDirty)
      return
    
    isDirty = false
    
    color = when (context) {
      null -> color
      else -> ContextCompat.getColor(context, color)
    }
    
    paint = when {
      stroke <= 0 -> PaintTools.basic(color)
      else -> PaintTools.strokeOnly(color, stroke)
    }
    
    _onBuild()
  }
}
