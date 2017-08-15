package com.kristal.library.appbase.view

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.widget.CardView
import android.util.AttributeSet

import com.kristal.library.appbase.R

/**
 * Created by Kristal on 6/20/2017.
 */

class RatioCardView : CardView {
  private var scale = -1

  constructor(context: Context) : super(context) {}

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init(attrs)
  }

  private fun init(attrs: AttributeSet) {
    val styleAttr = context.obtainStyledAttributes(attrs, R.styleable.RatioCardView)
    scale = styleAttr.getInt(R.styleable.RatioCardView_scale, -1)
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
