package com.kristal.library.appbase.extensions

import com.kristal.library.appbase.info.DeviceInfo

/**
 * Created by Kristal on 09/08/2017.
 */

val Float.px: Float
  get() = Math.round(DeviceInfo.density * this).toFloat()