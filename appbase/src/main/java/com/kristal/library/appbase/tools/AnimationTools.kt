package com.kristal.library.appbase.tools

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import com.kristal.library.appbase.R

object AnimationTools {
  fun load(pView: View, pResId: Int): Animation {
    cancel(pView)
    
    val lAnimation = AnimationUtils.loadAnimation(pView.context, pResId)
    pView.startAnimation(lAnimation)
    return lAnimation
  }
  
  private fun `in`(pView: View, pResId: Int): Animation? = when {
    pView.visibility == View.VISIBLE -> null
    else -> {
      pView.visibility = View.VISIBLE
      load(pView, pResId)
    }
  }
  
  private fun out(pView: View, pResId: Int): Animation? = when {
    pView.visibility == View.INVISIBLE -> null
    else -> {
      pView.visibility = View.INVISIBLE
      load(pView, pResId)
    }
  }
  
  fun cancel(pView: View) {
    val lAnimation = pView.animation ?: return
    lAnimation.setAnimationListener(null)
    lAnimation.cancel()
  }
  
  fun translate(pView: View, pFromXDelta: Float, pToXDelta: Float, pFromYDelta: Float, pToYDelta: Float, pDuration: Long, pListener: Animation.AnimationListener): Animation {
    cancel(pView)
    val lAnimation = TranslateAnimation(
        pFromXDelta,
        pToXDelta,
        pFromYDelta,
        pToYDelta)
    lAnimation.duration = pDuration
    lAnimation.interpolator = DecelerateInterpolator()
    lAnimation.setAnimationListener(pListener)
    pView.startAnimation(lAnimation)
    return lAnimation
  }
  
  fun alpha(pView: View, pFromAlpha: Float, pToAlpha: Float, pDuration: Long, pListener: Animation.AnimationListener): Animation {
    cancel(pView)
    val lAnimation = AlphaAnimation(pFromAlpha, pToAlpha)
    lAnimation.duration = pDuration
    lAnimation.setAnimationListener(pListener)
    pView.startAnimation(lAnimation)
    return lAnimation
  }
  
  fun fadeIn(pView: View) = `in`(pView, R.anim.fade_in)
  
  fun fadeOut(pView: View) = out(pView, R.anim.fade_out)
  
  fun slideLeftIn(pView: View) = `in`(pView, R.anim.slide_left_in)
  
  fun slideLeftOut(pView: View) = out(pView, R.anim.slide_left_out)
  
  fun slideTopIn(pView: View) = `in`(pView, R.anim.slide_top_in)
  
  fun slideTopOut(pView: View) = out(pView, R.anim.slide_top_out)
  
  fun slideRightIn(pView: View) = `in`(pView, R.anim.slide_right_in)
  
  fun slideRightOut(pView: View) = out(pView, R.anim.slide_right_out)
  
  fun slideBottomIn(pView: View) = `in`(pView, R.anim.slide_bottom_in)
  
  fun slideBottomOut(pView: View) = out(pView, R.anim.slide_bottom_out)
}
