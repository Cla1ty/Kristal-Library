package com.kristal.library.appbase.info

import android.content.Context
import com.kristal.library.appbase.tools.Trace

/**
 * Created by Kristal on 2/7/2017.
 */

object DeviceInfo {
  var screenWidth: Int = 0
    private set
  var screenHeight: Int = 0
    private set
  var density: Float = 0f
    private set
  var scaledDensity: Float = 0F
    private set
  
  fun init(pContext: Context) {
    val lDisplayMetrics = pContext.getResources().getDisplayMetrics()
    screenWidth = lDisplayMetrics.widthPixels
    screenHeight = lDisplayMetrics.heightPixels
    density = lDisplayMetrics.density
    scaledDensity = lDisplayMetrics.scaledDensity
    
    Trace.info(DeviceInfo::class.java)
  }
}
