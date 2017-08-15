package com.kristal.library.appbase.application

import android.app.Application
import com.kristal.library.appbase.info.AppInfo
import com.kristal.library.appbase.info.DeviceInfo
import com.kristal.library.appbase.tools.MathTools
import com.kristal.library.appbase.tools.VolleyTools

/**
 * Created by Kristal on 2/5/2017.
 */

class Application : Application() {
  override fun onCreate() {
    super.onCreate()
    
    AppInfo.init(baseContext)
    DeviceInfo.init(baseContext)
    
    MathTools.init(baseContext)
    VolleyTools.init(baseContext)
  }
}
