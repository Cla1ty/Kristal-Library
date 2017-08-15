package com.kristal.library.appbase.tools

import android.graphics.Matrix
import android.graphics.RectF
import android.widget.ImageView
import com.kristal.library.appbase.Size

/**
 * Created by USER on 3/17/2017.
 */

object MatrixTools {
  private val tmpSize = Size()
  private val tmpRectF1 = RectF()
  private val tmpRectF2 = RectF()
  private val tmpValue = FloatArray(9)
  
  /**
   * Calculate matrix scale dari source ke destination
   */
  fun scale(pMatrixOut: Matrix, pSrc: RectF, pDst: RectF) {
    pMatrixOut.reset()
    
    val xCenterSrc = pSrc.left + pSrc.width() / 2f
    val yCenterSrc = pSrc.top + pSrc.height() / 2f
    
    val scaleX = pDst.width() / pSrc.width()
    val scaleY = pDst.height() / pSrc.height()
    
    val xCenterDst = pDst.left + pDst.width() / 2f
    val yCenterDst = pDst.top + pDst.height() / 2f
    
    pMatrixOut.setScale(scaleX, scaleY, xCenterSrc, yCenterSrc)
    pMatrixOut.postTranslate(xCenterDst - xCenterSrc, yCenterDst - yCenterSrc)
    
    Trace.verbose("===== SCALE MATRIX =====" + "\nSrc: " + pSrc + "\nDst: " + pDst + "\nCenter src: " + xCenterSrc + "x" + yCenterSrc + "\nCenter dst: " + xCenterDst + "x" + yCenterDst + "\nScale: " + scaleX + "x" + scaleY + "\n===== end =====",
        1)
  }
  
  /**
   * Hanya calculate matrix center crop
   * Perlu perintah imageView.setMatrix(pMatrixOut)
   * untuk apply ke imageView
   */
  fun centerCrop(pMatrixOut: Matrix, pSrc: RectF, pDst: RectF) {
    pMatrixOut.reset()
    
    val xCenterSrc = pSrc.left + pSrc.width() / 2f
    val yCenterSrc = pSrc.top + pSrc.height() / 2f
    
    val scaleX = pDst.width() / pSrc.width()
    val scaleY = pDst.height() / pSrc.height()
    val scaleUse = Math.max(scaleX, scaleY)
    
    val xCenterDst = pDst.left + pDst.width() / 2f
    val yCenterDst = pDst.top + pDst.height() / 2f
    
    pMatrixOut.setScale(scaleUse, scaleUse, xCenterSrc, yCenterSrc)
    pMatrixOut.postTranslate(xCenterDst - xCenterSrc, yCenterDst - yCenterSrc)
    
    Trace.verbose("===== CENTER CROP MATRIX by SRC & DST =====" + "\nSrc: " + pSrc + "\nDst: " + pDst + "\nCenter src: " + xCenterSrc + "x" + yCenterSrc + "\nCenter dst: " + xCenterDst + "x" + yCenterDst + "\nScale use: " + scaleUse + "\n===== end =====",
        1)
  }
  
  /**
   * Hanya calculate matrix center crop
   * Perlu perintah imageView.setMatrix(pMatrixOut)
   * untuk apply ke imageView
   */
  fun centerCrop(pMatrixOut: Matrix, pView: ImageView) {
    val lSize = tmpSize
    val lSrc = tmpRectF1
    val lDst = tmpRectF2
    
    BitmapTools.getBitmapSize(lSize, pView)
    lSrc.set(0f, 0f, lSize.width.toFloat(), lSize.height.toFloat())
    
    val lViewWidth = pView.width.toFloat()
    val lViewHeight = pView.height.toFloat()
    lDst.set(0f, 0f, lViewWidth, lViewHeight)
    
    Trace.verbose("===== CENTER CROP MATRIX by IMAGE VIEW =====\nSrc: $lSrc\nDst: $lDst\n===== end =====")
    
    centerCrop(pMatrixOut, lSrc, lDst)
  }
  
  fun getBitmapRect(pRectOut: RectF, pMatrix: Matrix, pWidth: Int, pHeight: Int) {
    val lValue = tmpValue
    pMatrix.getValues(lValue)
    
    pRectOut.left = lValue[Matrix.MTRANS_X]
    pRectOut.top = lValue[Matrix.MTRANS_Y]
    pRectOut.right = pRectOut.left + Math.abs(lValue[Matrix.MSCALE_X]) * pWidth
    pRectOut.bottom = pRectOut.top + Math.abs(lValue[Matrix.MSCALE_Y]) * pHeight
    
    Trace.verbose("===== BITMAP RECT =====\n$pRectOut\n===== end =====")
  }
  
  fun getBitmapRect(pRectOut: RectF, pMatrix: Matrix, pView: ImageView) {
    val lSize = tmpSize
    BitmapTools.getBitmapSize(lSize, pView)
    
    getBitmapRect(pRectOut, pMatrix, lSize.width, lSize.height)
  }
}
