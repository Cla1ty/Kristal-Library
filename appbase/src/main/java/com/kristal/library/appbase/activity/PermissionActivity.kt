package com.kristal.library.appbase.activity

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.kristal.library.appbase.tools.Trace

/**
 * Created by Kristal on 5/29/2017.
 */

abstract class PermissionActivity : AppCompatActivity() {
  private var permissionGranted: (() -> Unit)? = null
  private var permissionDenied: (() -> Unit)? = null
  
  fun requestPermissions(pUsesPermission: Array<String>, pPermissionGranted: () -> Unit, pPermissionDenied: () -> Unit) {
    permissionGranted = pPermissionGranted
    permissionDenied = pPermissionDenied
    
    var lPermissionGranted = true
    for (lPermission in pUsesPermission) {
      if (ContextCompat.checkSelfPermission(this, lPermission) == PackageManager.PERMISSION_DENIED) {
        Trace.info("REQUEST PERMISSION: " + lPermission.substring(19))
        
        if (lPermissionGranted) {
          lPermissionGranted = false
          ActivityCompat.requestPermissions(this, pUsesPermission, 0)
        }
      }
    }
    
    if (lPermissionGranted) {
      val builder = StringBuilder()
      for (lPermission in pUsesPermission) {
        builder.append("\n")
        builder.append(lPermission.substring(19))
      }
      
      Trace.info("===== PERMISSION GRANTED =====$builder\n===== end =====")
      pPermissionGranted()
    }
  }
  
  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    if (requestCode == 0 && grantResults.isNotEmpty()) {
      for (index in 0 until grantResults.size) {
        Trace.info("${permissions[index].substring(19)}: ${if (grantResults[index] == -1) "DENIED" else "GRANTED"}")
      }
      
      val lPermissionGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
      if (lPermissionGranted) {
        Trace.info("PERMISSION_GRANTED")
        permissionGranted?.invoke()
      } else {
        Trace.info("PERMISSION_DENIED")
        permissionDenied?.invoke()
      }
    }
    
    permissionGranted = null
    permissionDenied = null
  }
}