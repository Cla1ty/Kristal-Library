package com.kristal.library.appbase.recyclerview

import com.kristal.library.appbase.tools.Trace
import java.lang.reflect.ParameterizedType

/**
 * Created by Kristal on 6/8/2017.
 */

object GenericTools {
  fun getClassFromInterface(cls: Class<*>, position: Int): Class<*>? {
    for (interfaces in cls.genericInterfaces) {
      if (interfaces is ParameterizedType) {
        Trace.debug("INTERFACE: " + interfaces)
        
        if (interfaces.actualTypeArguments.size < position) return null
        
        Trace.debug("Actual: " + interfaces.actualTypeArguments[position])
        return interfaces.actualTypeArguments[position] as Class<*>
      }
    }
    return null
  }
  
  fun getClassNameFromLamda(cls: Class<*>, position: Int): String {
    for (interfaces1 in cls.genericInterfaces) {
      if (interfaces1 is ParameterizedType) {
        Trace.debug("INTERFACE: " + interfaces1)
        
        val type = interfaces1.actualTypeArguments[0]
        val t = type.toString().substring(type.toString().indexOf('<') + 1, type.toString().indexOf('>'))
        Trace.debug("TT: $t")
        val s = t.split(",")
        Trace.debug("DD: ${s.size} $s")
        return s[position].trim()
      }
    }
    return "Unknown"
  }
}