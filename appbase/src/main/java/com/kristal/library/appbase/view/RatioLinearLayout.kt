package com.kristal.library.appbase.view

import android.content.Context
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.widget.LinearLayout
import com.kristal.library.appbase.R

/**
 * Created by Dwi on 7/12/2017.
 */

class RatioLinearLayout : LinearLayout {
  private var scale = -1
  
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init(attrs)
  }
  
  constructor(
      context: Context, attrs: AttributeSet, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init(attrs)
  }
  
  private fun init(attrs: AttributeSet) {
    val styleAttr = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout)
    scale = styleAttr.getInt(R.styleable.RatioFrameLayout_scale, -1)
    styleAttr.recycle()
  }
  
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    when (scale) {
      0 -> super.onMeasure(heightMeasureSpec, heightMeasureSpec)
      1 -> super.onMeasure(widthMeasureSpec, widthMeasureSpec)
      else -> super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
  }
}
