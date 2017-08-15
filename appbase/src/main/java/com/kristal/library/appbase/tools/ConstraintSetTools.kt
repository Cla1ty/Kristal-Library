package com.kristal.library.appbase.tools

import android.support.constraint.ConstraintSet
import android.view.View

/**
 * Created by USER on 3/26/2017.
 */

object ConstraintSetTools {
  
  fun connectToParent(pConstraintSetOut: ConstraintSet, pView: View) {
    connect(pConstraintSetOut, pView.id, (pView.parent as View).id)
  }
  
  fun connect(pConstraintSetOut: ConstraintSet, pViewId: Int, pParentId: Int) {
    pConstraintSetOut.connect(pViewId, ConstraintSet.LEFT, pParentId, ConstraintSet.LEFT)
    pConstraintSetOut.connect(pViewId, ConstraintSet.TOP, pParentId, ConstraintSet.TOP)
    pConstraintSetOut.connect(pViewId, ConstraintSet.RIGHT, pParentId, ConstraintSet.RIGHT)
    pConstraintSetOut.connect(pViewId, ConstraintSet.BOTTOM, pParentId, ConstraintSet.BOTTOM)
  }
  
  fun size(pConstraintSetOut: ConstraintSet, pViewId: Int, pWidth: Int, pHeight: Int) {
    pConstraintSetOut.constrainWidth(pViewId, pWidth)
    pConstraintSetOut.constrainHeight(pViewId, pHeight)
  }
  
  fun horizontalBias(pConstraintSetOut: ConstraintSet, pView: View, pLeft: Float) {
    horizontalBias(pConstraintSetOut,
        pView.id,
        pLeft,
        pView.width,
        (pView.parent as View).width)
    
  }
  
  fun horizontalBias(pConstraintSetOut: ConstraintSet, pViewId: Int, pLeft: Float, pViewWidth: Int, pParentWidth: Int) {
    val lBias = pLeft / (pParentWidth - pViewWidth).toFloat()
    Trace.verbose("===== Horizontal Bias: $lBias ===== Left: $pLeft,\t Parent Width: $pParentWidth,\t View Width: $pViewWidth")
    pConstraintSetOut.setHorizontalBias(pViewId, lBias)
  }
  
  fun verticalBias(pConstraintSetOut: ConstraintSet, pView: View, pTop: Float) {
    verticalBias(pConstraintSetOut,
        pView.id,
        pTop,
        pView.height,
        (pView.parent as View).height)
    
  }
  
  fun verticalBias(pConstraintSetOut: ConstraintSet, pViewId: Int, pTop: Float, pViewHeight: Int, pParentHeight: Int) {
    val lBias = pTop / (pParentHeight - pViewHeight).toFloat()
    Trace.verbose("===== Vertical Bias$lBias===== Top: $pTop,\t Parent Height: $pParentHeight,\t View Height: $pViewHeight")
    pConstraintSetOut.setVerticalBias(pViewId, lBias)
  }
}
