package com.kristal.library.appbase.tools

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * Created by Kristal on 7/14/2017.
 */

object StringTools {
  fun upperCaseFirstLetter(pString: String): String {
    val chars = pString.toCharArray()
    chars[0] = Character.toUpperCase(chars[0])
    return String(chars)
  }

//    fun base64ToString(base64String: String): String {
//        val data = Base64.decode(base64String, Base64.DEFAULT)
//        var s = String(data, "UTF-8")
//
//        return s
//    }
  
  fun getColorString(context: Context, colResId: Int): String {
    return "#" + Integer.toHexString(ContextCompat.getColor(context, colResId)).substring(2)
  }
}
