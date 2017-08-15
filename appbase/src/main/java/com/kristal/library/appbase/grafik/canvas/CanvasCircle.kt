package com.kristal.library.appbase.grafik.canvas

import android.content.Context
import android.graphics.Canvas

/**
 * Created by Kristal on 05/03/2017.
 */

class CanvasCircle(context: Context?) : BaseCanvas<CanvasCircle>(context) {
  override fun _onBuild() {}
  
  fun drawCircle(pCenterX: Float, pCenterY: Float, pRadius: Float, pCanvas: Canvas) {
    var pRadius = pRadius
    build()
    
    if (stroke > 0) {
      pRadius -= stroke
    }
    
    pCanvas.drawCircle(pCenterX, pCenterY, pRadius, paint!!)
  }
}
