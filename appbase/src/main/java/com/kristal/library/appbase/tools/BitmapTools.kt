package com.kristal.library.appbase.tools

import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ImageView
import com.kristal.library.appbase.R
import com.kristal.library.appbase.Size
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by USER on 3/17/2017.
 */

object BitmapTools {
  private val TAG = BitmapTools::class.java.simpleName
  
  private val tmp = Tmp()
  
  @TargetApi(Build.VERSION_CODES.KITKAT)
  fun getFilePath(context: Context, uri: Uri?): String {
    if (uri == null) {
      return ""
    }
    
    if ("file" == uri.scheme) {
      return uri.path
    }
    
    var currentApiVersion: Int
    try {
      currentApiVersion = Build.VERSION.SDK_INT
    } catch (e: NumberFormatException) {
      //API 3 will crash if SDK_INT is called
      currentApiVersion = 3
    }
    
    val cursor: Cursor? = null
    try {
      if (currentApiVersion >= Build.VERSION_CODES.KITKAT && DocumentsContract
          .isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
          val docId = DocumentsContract.getDocumentId(uri)
          val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
          val type = split[0]
          if ("primary".equals(type, ignoreCase = true)) {
            return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
          }
        } else if (isDownloadsDocument(uri)) { // DownloadsProvider
          val id = DocumentsContract.getDocumentId(uri)
          val contentUri = ContentUris
              .withAppendedId(Uri.parse("content://downloads/public_downloads"),
                  java.lang.Long.valueOf(id)!!)
          return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) { // MediaProvider
          val docId = DocumentsContract.getDocumentId(uri)
          val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
          val type = split[0]
          
          val contentUri = when (type) {
            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            else -> null
          }
          
          val selection = "_id=?"
          val selectionArgs = arrayOf(split[1])
          
          return getDataColumn(context, contentUri, selection, selectionArgs)
        }
      } else {
        return getDataColumn(context, uri, null, null)
      }
    } finally {
      cursor?.close()
    }
    return ""
  }
  
  /**
   * Get the value of the data column for this Uri. This is useful for
   * MediaStore Uris, and other file-based ContentProviders.
   
   * @param context       The context.
   * *
   * @param uri           The Uri to query.
   * *
   * @param selection     (Optional) Filter used in the query.
   * *
   * @param selectionArgs (Optional) Selection arguments used in the query.
   * *
   * @return The value of the _data column, which is typically a file path.
   */
  fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String {
    
    var cursor: Cursor? = null
    val column = MediaStore.Images.Media.DATA
    val projection = arrayOf(column)
    try {
      cursor = context.contentResolver
          .query(uri, projection, selection, selectionArgs, null)
      if (cursor != null && cursor.moveToFirst()) {
        val column_index = cursor.getColumnIndexOrThrow(column)
        return cursor.getString(column_index)
      }
    } catch (e: Exception) {
    } finally {
      if (cursor != null)
        cursor.close()
    }
    return ""
  }
  
  fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
  }
  
  /**
   * @param uri The Uri to check.
   * *
   * @return Whether the Uri authority is DownloadsProvider.
   */
  fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
  }
  
  /**
   * @param uri The Uri to check.
   * *
   * @return Whether the Uri authority is MediaProvider.
   */
  fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
  }
  
  fun getExifOrientation(pContext: Context, pUri: Uri): Int {
    val filePath = getFilePath(pContext, pUri)
    return getExifOrientation(filePath)
  }
  
  fun getExifOrientation(pPath: String?): Int {
    try {
      if (pPath != null && pPath != "") {
        val exif = ExifInterface(pPath)
        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED)
      }
    } catch (e: IOException) {
      
    }
    
    return ExifInterface.ORIENTATION_UNDEFINED
  }
  
  @Throws(IOException::class)
  @JvmOverloads fun calculateBitmapSampleSize(pPath: String, pWidth: Int, pHeight: Int, orientation: Int = getExifOrientation(pPath)): Int {
    val lOptions = BitmapFactory.Options()
    lOptions.inJustDecodeBounds = true
    
    BitmapFactory.decodeFile(pPath, lOptions)
    var imageWidth = lOptions.outWidth
    var imageHeight = lOptions.outHeight
    
    if (orientation == ExifInterface.ORIENTATION_ROTATE_90 || orientation == ExifInterface.ORIENTATION_ROTATE_270) {
      imageWidth = lOptions.outHeight
      imageHeight = lOptions.outWidth
    }
    
    var sampleSize = 1
    while (imageHeight / (sampleSize shl 1) >= pHeight && imageWidth / (sampleSize shl 1) >= pWidth) {
      sampleSize = sampleSize shl 1
    }
    return sampleSize
  }
  
  @Throws(IOException::class)
  fun calculateBitmapSampleSize(context: Context, imageUri: Uri, targetWidth: Int, targetHeight: Int, orientation: Int): Int {
    if (targetWidth == 0 || targetHeight == 0) {
      return 1
    }
    var `is`: InputStream? = null
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    try {
      `is` = context.contentResolver.openInputStream(imageUri)
      BitmapFactory.decodeStream(`is`, null, options) // Just get image size
    } finally {
      if (`is` != null)
        try {
          `is`.close()
        } catch (t: Throwable) {
        }
      
    }
    
    var imageWidth = options.outWidth
    var imageHeight = options.outHeight
    //Log.e(LOG_TAG, "FrameSize :" + options.outWidth + "xGlobal" + options.outHeight + ", Orientation:" + orientation);
    if (orientation == ExifInterface.ORIENTATION_ROTATE_90 || orientation == ExifInterface.ORIENTATION_ROTATE_270) {
      imageWidth = options.outHeight
      imageHeight = options.outWidth
    }
    
    var sampleSize = 1
    while (imageHeight / (sampleSize shl 1) >= targetHeight && imageWidth / (sampleSize shl 1) >= targetWidth) {
      sampleSize = sampleSize shl 1
    }
    return sampleSize
  }
  
  fun getBitmapSize(pSizeOut: Size, pView: ImageView) {
    val lDrawable = pView.drawable
    if (lDrawable is BitmapDrawable) {
      val lBitmap = lDrawable.bitmap
      if (lBitmap != null) {
        pSizeOut.width = lBitmap.width
        pSizeOut.height = lBitmap.height
      }
    }
  }
  
  fun getBitmapRectF(pOutRectF: RectF, pView: ImageView) {
    val lRect = Rect()
    getBitmapRect(lRect, pView)
    
    val lBitmap = getBitmap(pView)
    if (lBitmap == null) {
      pOutRectF.set(0f, 0f, 1f, 1f)
      return
    }
    
    val viewValues = FloatArray(9)
    pView.imageMatrix.getValues(viewValues)
    
    val lLeft = lRect.left / (lBitmap.width * 1f)
    val lTop = lRect.top / (lBitmap.height * 1f)
    val lRight = lRect.right / (lBitmap.width * 1f)
    val lBottom = lRect.bottom / (lBitmap.height * 1f)
    pOutRectF.set(lLeft, lTop, lRight, lBottom)
  }
  
  fun getBitmapRect(pOutRect: Rect, pView: ImageView) {
    val viewValues = FloatArray(9)
    pView.imageMatrix.getValues(viewValues)
    
    val x = (-viewValues[Matrix.MTRANS_X] / viewValues[Matrix.MSCALE_X]).toInt()
    val y = (-viewValues[Matrix.MTRANS_Y] / viewValues[Matrix.MSCALE_Y]).toInt()
    val width = (pView.width / viewValues[Matrix.MSCALE_X]).toInt()
    val height = (pView.height / viewValues[Matrix.MSCALE_Y]).toInt()
    pOutRect.set(x, y, x + width, y + height)
  }
  
  fun setColorFilter(pView: ImageView, pResColor: Int) {
    setColorFilter(pView.drawable, pResColor, pView.context)
  }
  
  fun setColorFilter(pDrawable: Drawable?, pResColor: Int, pContext: Context) {
    if (pDrawable == null)
      return
    
    val lColor = ContextCompat.getColor(pContext, pResColor)
    pDrawable.mutate().setColorFilter(lColor, PorterDuff.Mode.SRC_IN)
  }
  
  fun setColorFilterMultiply(pView: ImageView, pResColor: Int) {
    setColorFilterMultiply(pView.drawable, pResColor, pView.context)
  }
  
  fun setColorFilterMultiply(pDrawable: Drawable?, pResColor: Int, pContext: Context) {
    if (pDrawable == null)
      return
    
    val lColor = ContextCompat.getColor(pContext, pResColor)
    pDrawable.mutate().setColorFilter(lColor, PorterDuff.Mode.MULTIPLY)
  }
  
  fun removeColorFilter(pView: ImageView) {
    val lDrawable = pView.drawable ?: return
    
    lDrawable.mutate().colorFilter = null
  }
  
  fun save(image: Bitmap, outFile: File, pFormat: Bitmap.CompressFormat): Boolean {
    if (outFile.exists()) {
      outFile.delete()
    }
    
    try {
      image.compress(pFormat, 100, FileOutputStream(outFile))
      return true
    } catch (e: FileNotFoundException) {
      Log.e(TAG, "FileNotFoundException:" + e.message)
    } catch (e: Exception) {
      Log.e(TAG, "Exception:" + e.message)
    }
    
    return false
  }
  
  fun getBitmap(pView: ImageView): Bitmap? {
    val lDrawable = pView.drawable
    if (lDrawable is BitmapDrawable)
      return lDrawable.bitmap
    
    return null
  }
  
  fun load(pPath: String, width: Int, height: Int): Bitmap {
    var sampler = 1
    try {
      sampler = calculateBitmapSampleSize(pPath, width, height)
    } catch (e: IOException) {
      e.printStackTrace()
    }
    
    val options = BitmapFactory.Options()
    options.inSampleSize = sampler
    
    val lBitmap = BitmapFactory.decodeFile(pPath, options)
    val lBitmapRotate = rotate(lBitmap, getExifOrientation(pPath).toFloat())
    //        lBitmap.recycler();
    return lBitmapRotate
  }
  
  fun rotate(paramBitmap: Bitmap, paramRotate: Float): Bitmap {
    if (paramRotate == 0f)
      return paramBitmap
    
    val matrix = Matrix()
    matrix.preRotate(paramRotate, (paramBitmap.width / 2).toFloat(), (paramBitmap.height / 2).toFloat())
    return Bitmap.createBitmap(paramBitmap,
        0,
        0,
        paramBitmap.width,
        paramBitmap.height,
        matrix,
        true)
  }
  
  fun load(pPath: String): Bitmap {
    val lBitmap = BitmapFactory.decodeFile(pPath)
    return rotate(lBitmap, getExifOrientation(pPath).toFloat())
  }
  
  fun crop(pView: ImageView, pPath: Path): Bitmap? {
    val lBitmap = getBitmap(pView) ?: return null
    Trace.verbose("===== crop =====")
    
    val lSrc = tmp.src
    val lDst = tmp.dst
    val lPath = tmp.path
    val lMatrix = tmp.matrix
    
    lSrc.set(0f, 0f, pView.width.toFloat(), pView.height.toFloat())
    pPath.computeBounds(lDst, false)
    MatrixTools.scale(lMatrix, lSrc, lDst)
    
    lPath.set(pPath)
    lPath.transform(lMatrix)
    
    val lPaint = PaintTools.basic(android.R.color.black, pView.context)
    val lPaintFermode = PaintTools.basic(R.color.red_300, pView.context)
    lPaintFermode.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    
    val lBitmapMatrix = pView.imageMatrix
    
    val lBitmapCanvas = Bitmap
        .createBitmap(pView.width, pView.height, Bitmap.Config.ARGB_8888)
    val lCanvas = Canvas(lBitmapCanvas)
    
    lCanvas.drawPath(lPath, lPaint)
    lCanvas.drawBitmap(lBitmap, lBitmapMatrix, lPaintFermode)
    
    return lBitmapCanvas
  }
  
  fun createPattern(pResColorBg: Int, pResColorPattern: Int, pResId: Int, pContext: Context): Bitmap {
    var lBitmap: Bitmap? = null
    if (pResId != 0)
      lBitmap = getBitmapVector(pResId, pContext)
    return createPattern(pResColorBg, pResColorPattern, lBitmap, pContext)
  }
  
  fun createPattern(pResColorBg: Int, pResColorPattern: Int, pBitmap: Bitmap?, pContext: Context): Bitmap {
    if (pBitmap == null) {
      return createPattern(pResColorBg, pResColorPattern, null, 1, 1, pContext)
    }
    
    val lBitmapWidth = pBitmap.width
    val lBitmapHeight = pBitmap.height
    return createPattern(pResColorBg,
        pResColorPattern,
        pBitmap,
        lBitmapWidth,
        lBitmapHeight,
        pContext)
  }
  
  fun createPattern(pResColorBg: Int, pResColorPattern: Int, pResId: Int, pTargetWidth: Int, pTargetHeight: Int, pContext: Context): Bitmap {
    return createPattern(pResColorBg,
        pResColorPattern,
        getBitmapVector(pResId, pContext),
        pTargetWidth,
        pTargetHeight,
        pContext)
  }
  
  fun createPattern(pResColorBg: Int, pResColorPattern: Int, pBitmap: Bitmap?, pTargetWidth: Int, pTargetHeight: Int, pContext: Context): Bitmap {
    var pResColorBg = pResColorBg
    val lBitmap: Bitmap
    if (pBitmap != null) {
      val lBitmapWidth = pBitmap.width
      val lBitmapHeight = pBitmap.height
      
      val lBitmapPattern = Bitmap
          .createBitmap(pTargetWidth, pTargetHeight, Bitmap.Config.ARGB_8888)
      val lCanvasPattern = Canvas(lBitmapPattern)
      
      var i = 0
      while (i < pTargetWidth) {
        var j = 0
        while (j < pTargetHeight) {
          lCanvasPattern.drawBitmap(pBitmap, i.toFloat(), j.toFloat(), null)
          j += lBitmapHeight
        }
        i += lBitmapWidth
      }
      
      if (pResColorPattern != 0) {
        val lColorPattern = ContextCompat.getColor(pContext, pResColorPattern)
        lCanvasPattern.drawColor(lColorPattern, PorterDuff.Mode.SRC_IN)
      }
      
      lBitmap = Bitmap.createBitmap(pTargetWidth, pTargetHeight, Bitmap.Config.ARGB_8888)
      val lCanvas = Canvas(lBitmap)
      
      pResColorBg = if (pResColorBg == 0)
        android.R.color.white
      else
        pResColorBg
      val lColorBg = ContextCompat.getColor(pContext, pResColorBg)
      lCanvas.drawColor(lColorBg)
      
      lCanvas.drawBitmap(lBitmapPattern, 0f, 0f, null)
      lBitmapPattern.recycle()
    } else {
      lBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
      val lCanvas = Canvas(lBitmap)
      
      pResColorBg = if (pResColorBg == 0)
        android.R.color.white
      else
        pResColorBg
      val lColorBg = ContextCompat.getColor(pContext, pResColorBg)
      lCanvas.drawColor(lColorBg)
    }
    
    return lBitmap
  }
  
  fun getBitmapTiled(pBitmap: Bitmap, pTargetWidth: Int, pTargetHeight: Int): Bitmap {
    val lBitmapPattern = Bitmap
        .createBitmap(pTargetWidth, pTargetHeight, Bitmap.Config.ARGB_8888)
    val lCanvasPattern = Canvas(lBitmapPattern)
    
    val lBitmapWidth = pBitmap.width
    val lBitmapHeight = pBitmap.height
    
    var i = 0
    while (i < pTargetWidth) {
      var j = 0
      while (j < pTargetHeight) {
        lCanvasPattern.drawBitmap(pBitmap, i.toFloat(), j.toFloat(), null)
        j += lBitmapHeight
      }
      i += lBitmapWidth
    }
    
    return lBitmapPattern
  }
  
  fun getBitmap(drawableId: Int, context: Context): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    if (drawable is BitmapDrawable) {
      return drawable.bitmap
    } else if (drawable is VectorDrawable) {
      return getBitmap(drawable)
    } else {
      throw IllegalArgumentException("unsupported drawable type")
    }
  }
  
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
    val bitmap = Bitmap.createBitmap(vectorDrawable.minimumWidth,
        vectorDrawable.minimumHeight,
        Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
  }
  
  @JvmOverloads fun getBitmapVector(drawableId: Int, context: Context, pTargetWidth: Int = 0, pTargetHeight: Int = 0): Bitmap? {
    var pTargetWidth = pTargetWidth
    var pTargetHeight = pTargetHeight
    if (drawableId == 0)
      return null
    
    val vectorDrawable = VectorDrawableCompat
        .create(context.resources, drawableId, null) ?: return null
    
    pTargetWidth = if (pTargetWidth == 0)
      vectorDrawable.intrinsicWidth
    else
      pTargetWidth
    pTargetHeight = if (pTargetHeight == 0)
      vectorDrawable.intrinsicHeight
    else
      pTargetHeight
    
    val bitmap = Bitmap.createBitmap(pTargetWidth, pTargetHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
  }
  
  private class Tmp {
    val path = Path()
    val src = RectF()
    val dst = RectF()
    val matrix = Matrix()
  }
}
