package com.kristal.library.appbase.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Kristal on 09/08/2017.
 */

fun ImageView.url(pPath: String) {
  Glide.with(context).load(pPath).into(this)
}