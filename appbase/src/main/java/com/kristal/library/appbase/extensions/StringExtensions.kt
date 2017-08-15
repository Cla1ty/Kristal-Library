package com.kristal.library.appbase.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kristal on 10/08/2017.
 */

fun String.toLocalDate(): String {
  val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
  val localFormat = SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm", Locale.getDefault())
  val date = serverFormat.parse(this)
  return localFormat.format(date)
}