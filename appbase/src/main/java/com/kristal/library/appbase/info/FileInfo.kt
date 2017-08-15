package com.kristal.library.appbase.info

import android.Manifest
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.content.ContextCompat

import com.kristal.library.appbase.activity.PermissionActivity
import com.kristal.library.appbase.tools.FileTools
import com.kristal.library.appbase.tools.Trace

import java.io.File

/**
 * Created by Kristal on 4/11/2017.
 */

object FileInfo {
  private val DIR_EXTERNAL = AppInfo.appName
  private val FILE_IMAGE = "image-%s.jpg"
  private val FILE_VIDEO = "video-%s.mp4"
  
  private var externalDir: File? = null
  private var cacheDir: File? = null
  
  fun with(pActivity: PermissionActivity) {
    if (ContextCompat.checkSelfPermission(pActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
      Trace.warm("Dont forget request permission \"WRITE_EXTERNAL_STORAGE\" when use this class")
    }

//        pActivity.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                {
    externalDir = File(Environment.getExternalStorageDirectory(), DIR_EXTERNAL)
    externalDir!!.mkdirs()
    cacheDir = pActivity.cacheDir
    
    Trace.info("FILE INFO",
        "Cache Dir: " + cacheDir!!.path,
        "File Dir: " + pActivity.filesDir.path,
        "External Dir: " + externalDir!!.path,
        "External Cache Dir: " + pActivity.externalCacheDir?.path)
//                }, { })
  }
  
  fun generateFileImage(): File {
    return File(externalDir, String
        .format(FILE_IMAGE, FileTools.generateSimpleDateFormat()))
  }
  
  fun generateFileVideo(): File {
    return File(externalDir, String
        .format(FILE_VIDEO, FileTools.generateSimpleDateFormat()))
  }
}
