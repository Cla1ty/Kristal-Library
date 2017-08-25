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
  
  fun init(context: Context) {
    try {
      val lPackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
      val lInfo = context.applicationInfo
      val lStringId = lInfo.labelRes
      
      appName = when (lStringId) {
        0 -> lInfo.nonLocalizedLabel.toString()
        else -> context.getString(lStringId)
      }
      versionCode = lPackageInfo.versionCode
      versionName = lPackageInfo.versionName
      packageName = context.packageName
      label = context.packageManager.getApplicationLabel(lInfo) as String
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
    }
    
    Trace.info(AppInfo::class.java)
  }
}
