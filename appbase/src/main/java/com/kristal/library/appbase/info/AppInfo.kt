package com.kristal.library.appbase.info

import android.content.Context
import android.content.pm.PackageManager
import com.kristal.library.appbase.tools.Trace

/**
 * Created by Kristal on 2/7/2017.
 */

object AppInfo {
  var versionCode: Int = -1
    private set
  var appName: String = "Unknown"
    private set
  var versionName: String = "Unknown"
    private set
  var label: String = "Unknown"
    private set
  var packageName: String = "Unknown"
    private set
  
  fun init(pContext: Context) {
    try {
      val lPackageInfo = pContext.packageManager.getPackageInfo(pContext.packageName, 0)
      val lInfo = pContext.applicationInfo
      val stringId = lInfo.labelRes
      
      appName = when (stringId) {
        0 -> lInfo.nonLocalizedLabel.toString()
        else -> pContext.getString(stringId)
      }
      versionCode = lPackageInfo.versionCode
      versionName = lPackageInfo.versionName
      packageName = pContext.packageName
      label = pContext.packageManager.getApplicationLabel(lInfo) as String
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
    }
    
    Trace.info(AppInfo::class.java)
  }
}
