package com.kristal.library.appbase.tools

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kristal on 18/02/2017.
 */

object FileTools {
  fun generateSimpleDateFormat(): String {
    val lSimpleDateFormat = SimpleDateFormat("yyyyMMdd-HH_mm_ss", Locale.getDefault())
    val lCurrentDate = Date(System.currentTimeMillis())
    return lSimpleDateFormat.format(lCurrentDate)
  }
  
  fun generateName(pName: String) = File(generateSimpleDateFormat() + pName)
  
  fun convertToUri(pPath: String) = convertToUri(File(pPath))
  
  fun convertToUri(pFile: File) = Uri.fromFile(pFile)
  
  fun getCacheFile(context: Context, pUri: String): File? {
    var file: File? = null
    try {
      val fileName = Uri.parse(pUri).lastPathSegment
      file = File.createTempFile(fileName, null, context.cacheDir)
    } catch (e: IOException) {
    }
    
    return file
  }
}
