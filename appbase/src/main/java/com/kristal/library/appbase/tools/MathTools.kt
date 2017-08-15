package com.kristal.library.appbase.tools

import android.content.Context
import android.graphics.PointF
import android.view.MotionEvent
import java.util.*

/**
 * Created by Kristal on 2/12/2017.
 */

object MathTools {
  private var scaledDensity: Float = 0F
  private var density: Float = 0f
  private var random: Random? = null
  
  fun init(pContext: Context) {
    density = pContext.resources.displayMetrics.density
    scaledDensity = pContext.resources.displayMetrics.scaledDensity
    random = Random()
  }
  
  fun getRandom(pMin: Float, pMax: Float) = (pMax - pMin) * random!!.nextFloat() + pMin
  
  fun getRandom(pMin: Int, pMax: Int) = random!!.nextInt(pMax - pMin + 1) + pMin
  
  fun <T> getRandom(pList: Array<T>) = pList[getRandom(0, pList.size)]
  
  fun dpToPx(pDensity: Float) = Math.round(density * pDensity)
  
  fun pxToSp(px: Float): Float {
    return px / scaledDensity
  }
  
  fun <T : Comparable<T>> clamp(pVal: T, pMin: T, pMax: T): T = when {
    pVal.compareTo(pMin) < 0 -> pMin
    pVal.compareTo(pMax) > 0 -> pMax
    else -> pVal
  }
  
  fun round(pVal: Float, pRound: Int): Float = when {
    pRound <= 0 -> Math.round(pVal).toFloat()
    else -> {
      val lTmp = Math.pow(10.0, pRound.toDouble())
      Math.round(pVal * lTmp) / lTmp.toFloat()
    }
  }
  
  fun spacing(event: MotionEvent): Float {
    val x = event.getX(0) - event.getX(1)
    val y = event.getY(0) - event.getY(1)
    return Math.sqrt((x * x + y * y).toDouble()).toFloat()
  }
  
  fun midPoint(point: PointF, event: MotionEvent) {
    val x = event.getX(0) + event.getX(1)
    val y = event.getY(0) + event.getY(1)
    point.set(x / 2f, y / 2f)
  }
  
  fun rotation(event: MotionEvent): Float {
    val delta_x = (event.getX(0) - event.getX(1)).toDouble()
    val delta_y = (event.getY(0) - event.getY(1)).toDouble()
    val radians = Math.atan2(delta_y, delta_x)
    
    return Math.toDegrees(radians).toFloat()
  }
}
