package com.kristal.library.appbase.viewpager.animation

import android.support.v4.view.ViewPager
import android.view.View

/**
 * Created by USER on 5/22/2017.
 */

class DepthPageTransformer(private val minScale: Float = DepthPageTransformer.DEFAULT_MIN_SCALE)
  : ViewPager.PageTransformer {
  companion object {
    val DEFAULT_MIN_SCALE = 0.75f
  }
  
  override fun transformPage(view: View, position: Float) {
    val pageWidth = view.width
    
    when {
      position < -1 || position > 1 -> view.alpha = 0f
      position <= 0 -> {
        view.alpha = 1f
        view.translationX = 0f
        view.scaleX = 1f
        view.scaleY = 1f
      }
      position <= 1 -> {
        view.alpha = 1 - position
        view.translationX = pageWidth * -position
        
        val scaleFactor = minScale + (1 - minScale) * (1 - Math.abs(position))
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
      }
    }
  }
}
