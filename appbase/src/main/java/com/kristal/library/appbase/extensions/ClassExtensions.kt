package com.kristal.library.appbase.extensions

/**
 * Created by Kristal on 6/8/2017.
 */

val Any.className: String
  get() {
    return this::class.java.simpleName
  }