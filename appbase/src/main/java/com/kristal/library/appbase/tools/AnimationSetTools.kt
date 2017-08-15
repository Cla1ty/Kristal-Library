package com.kristal.library.appbase.tools

import android.graphics.Rect
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation

object AnimationSetTools {
  fun zoomInOut(pView: View, pToScale: Float) {
    AnimationTools.cancel(pView)
    
    val lFromXScale = pView.scaleX
    val lFromYScale = pView.scaleY
    
    val lAnimationScaleUp = ScaleAnimation(lFromXScale,
        lFromXScale * pToScale,
        lFromYScale,
        lFromYScale * pToScale)
    val lAnimationScaleDown = ScaleAnimation(lFromXScale * pToScale,
        lFromXScale,
        lFromYScale * pToScale,
        lFromYScale)
    
    val lAnimationSet = AnimationSet(false)
    lAnimationSet.duration = 600
    lAnimationSet.addAnimation(lAnimationScaleUp)
    lAnimationSet.addAnimation(lAnimationScaleDown)
    pView.startAnimation(lAnimationSet)
  }
  
  fun moveAndScalePositive(pView: View, pSrcRect: Rect, pDestRect: Rect, pDuration: Long, pInterpolator: Interpolator?, pListener: Animation.AnimationListener) {
    AnimationTools.cancel(pView)
    
    val lScaleX = (pDestRect.width() / pSrcRect.width()).toFloat()
    val lScaleY = (pDestRect.height() / pSrcRect.height()).toFloat()
    val lTranslateX = (pDestRect.centerX() - pSrcRect.centerX()).toFloat()
    val lTranslateY = (pDestRect.centerY() - pSrcRect.centerY()).toFloat()
    
    val lScaleAnimation = ScaleAnimation(1f, lScaleX, 1f, lScaleY)
    val lTranslateAnimation = TranslateAnimation(0f,
        lTranslateX,
        0f,
        lTranslateY)
    
    val lAnimationSet = AnimationSet(true)
    lAnimationSet.interpolator = DecelerateInterpolator()
    lAnimationSet.addAnimation(lScaleAnimation)
    lAnimationSet.addAnimation(lTranslateAnimation)
    lAnimationSet.setAnimationListener(pListener)
    lAnimationSet.duration = pDuration
    if (pInterpolator != null) {
      lAnimationSet.interpolator = pInterpolator
    }
    pView.startAnimation(lAnimationSet)
  }
  
  fun moveAndScaleReverse(pView: View, pSrcRect: Rect, pDestRect: Rect, pDuration: Long, pInterpolator: Interpolator?, pListener: Animation.AnimationListener) {
    AnimationTools.cancel(pView)
    
    val lScaleX = (pDestRect.width() / pSrcRect.width()).toFloat()
    val lScaleY = (pDestRect.height() / pSrcRect.height()).toFloat()
    val lTranslateX = (pDestRect.centerX() - pSrcRect.centerX()).toFloat()
    val lTranslateY = (pDestRect.centerY() - pSrcRect.centerY()).toFloat()
    
    val lScaleAnimation = ScaleAnimation(1 / lScaleX, 1f, 1 / lScaleY, 1f)
    val lTranslateAnimation = TranslateAnimation(-lTranslateX,
        0f,
        -lTranslateY,
        0f)
    
    val lAnimationSet = AnimationSet(true)
    lAnimationSet.interpolator = DecelerateInterpolator()
    lAnimationSet.addAnimation(lScaleAnimation)
    lAnimationSet.addAnimation(lTranslateAnimation)
    lAnimationSet.setAnimationListener(pListener)
    lAnimationSet.duration = pDuration
    if (pInterpolator != null) {
      lAnimationSet.interpolator = pInterpolator
    }
    pView.startAnimation(lAnimationSet)
  }
  
  fun moveAndScale(pView: View, pFromRect: Rect, pToRect: Rect, pSrcRect: Rect, pDuration: Long, pInterpolator: Interpolator?, pAnimationListener: Animation.AnimationListener) {
    AnimationTools.cancel(pView)
    
    val lScaleX = pView.scaleX
    val lScaleY = pView.scaleY
    val lTranslationX = pView.translationX
    val lTranslationY = pView.translationY
    
    val lFromXScale = pFromRect.width() / pSrcRect.width() * lScaleX
    val lToXScale = pToRect.width() / pSrcRect.width() * lScaleX
    val lFromYScale = pFromRect.height() / pSrcRect.height() * lScaleY
    val lToYScale = pToRect.height() / pSrcRect.height() * lScaleY
    
    val lFromXTranslate = pFromRect.centerX() - pSrcRect.centerX() + lTranslationX
    val lToXTranslate = pToRect.centerX() - pSrcRect.centerX() + lTranslationX
    val lFromYTranslate = pFromRect.centerY() - pSrcRect.centerY() + lTranslationY
    val lToYTranslate = pToRect.centerY() - pSrcRect.centerY() + lTranslationY
    
    val lScaleAnimation = ScaleAnimation(lFromXScale,
        lToXScale,
        lFromYScale,
        lToYScale)
    val lTranslateAnimation = TranslateAnimation(lFromXTranslate,
        lToXTranslate,
        lFromYTranslate,
        lToYTranslate)
    
    val lAnimationSet = AnimationSet(true)
    lAnimationSet.addAnimation(lScaleAnimation)
    lAnimationSet.addAnimation(lTranslateAnimation)
    lAnimationSet.setAnimationListener(pAnimationListener)
    lAnimationSet.duration = pDuration
    if (pInterpolator != null) {
      lAnimationSet.interpolator = pInterpolator
    } else {
      lAnimationSet.interpolator = DecelerateInterpolator()
    }
    pView.startAnimation(lAnimationSet)
  }
  
  fun moveAndScale(pView: View, pFromRect: Rect, pToRect: Rect, pDuration: Long, pInterpolator: Interpolator, pListener: Animation.AnimationListener) {
    moveAndScale(pView,
        pFromRect,
        pToRect,
        ViewTools.getGlobalVisibleRect(pView),
        pDuration,
        pInterpolator,
        pListener)
  }
}
