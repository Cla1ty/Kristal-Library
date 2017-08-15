package com.kristal.library.appbase.tools

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.support.v4.content.ContextCompat

/**
 * Created by Kristal on 2/12/2017.
 */

object PaintTools {
  private fun getPaint(pType: Type, pColor: Int, pSize: Float): Paint {
    val lPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    lPaint.color = pColor
    when (pType) {
      PaintTools.Type.STROKE -> lPaint.strokeWidth = pSize
      PaintTools.Type.STROKE_ONLY -> {
        lPaint.strokeWidth = pSize
        lPaint.style = Paint.Style.STROKE
      }
      PaintTools.Type.TEXT -> lPaint.textSize = pSize
      PaintTools.Type.FERMODE -> lPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
      else -> {
      }
    }
    return lPaint
  }
  
  private fun getPaint(pType: Type, pResId: Int, pSize: Float, pContext: Context) = getPaint(pType, ContextCompat.getColor(pContext, pResId), pSize)
  
  fun fermode() = getPaint(Type.FERMODE, Color.WHITE, 0f)
  
  fun basic(pColor: Int) = getPaint(Type.DEFAULT, pColor, 0f)
  
  fun basic(pResId: Int, pContext: Context) = getPaint(Type.DEFAULT, pResId, 0f, pContext)
  
  fun stroke(pColor: Int, pSize: Float) = getPaint(Type.STROKE, pColor, pSize)
  
  fun stroke(pResId: Int, pSize: Float, pContext: Context) = getPaint(Type.STROKE, pResId, pSize, pContext)
  
  fun strokeOnly(pColor: Int, pSize: Float) = getPaint(Type.STROKE_ONLY, pColor, pSize)
  
  fun strokeOnly(pResId: Int, pSize: Float, pContext: Context) = getPaint(Type.STROKE_ONLY, pResId, pSize, pContext)
  
  fun text(pColor: Int, pSize: Float) = getPaint(Type.TEXT, pColor, pSize)
  
  fun text(pResId: Int, pSize: Float, pContext: Context) = getPaint(Type.TEXT, pResId, pSize, pContext)
  
  internal enum class Type {
    DEFAULT,
    STROKE,
    STROKE_ONLY,
    TEXT,
    FERMODE
  }
}
