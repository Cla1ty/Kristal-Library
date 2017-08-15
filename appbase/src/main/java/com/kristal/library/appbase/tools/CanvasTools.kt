package com.kristal.library.appbase.tools

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff

/**
 * Created by USER on 4/3/2017.
 */

object CanvasTools {
  fun clearBitmap(pCanvas: Canvas) {
    pCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
  }
}
