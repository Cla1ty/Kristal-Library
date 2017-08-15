package com.kristal.library.appbase.tools

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Kristal on 7/12/2017.
 */

object JsonTools {
  fun <T> getValue(jsonObject: JSONObject, pClass: Any): T {
    Trace.info("JSON: " + jsonObject.toString())
    
    try {
      val fields = pClass::class.java.declaredFields
      for (i in 0..jsonObject.length() - 1) {
        val realKey = jsonObject.names().getString(i)
        val names = realKey.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var newKey = names[0]
        for (j in 1..names.size - 1) {
          newKey += StringTools.upperCaseFirstLetter(names[j])
        }
        
        var contain = false
        for (field in fields) {
          if (field.name == newKey) {
            contain = true
            field.isAccessible = true
            if (Int::class.javaPrimitiveType!!.isAssignableFrom(field.type)) field.setInt(pClass, jsonObject.getInt(realKey))
            else if (String::class.java.isAssignableFrom(field.type)) field.set(pClass, jsonObject.getString(realKey))
            else if (Boolean::class.javaPrimitiveType!!.isAssignableFrom(field.type)) field.setBoolean(pClass, jsonObject.getBoolean(realKey))
            else if (Double::class.javaPrimitiveType!!.isAssignableFrom(field.type)) field.setDouble(pClass, jsonObject.getDouble(realKey))
            else if (Long::class.javaPrimitiveType!!.isAssignableFrom(field.type)) field.setLong(pClass, jsonObject.getLong(realKey))
            
            break
          }
        }
        
        if (!contain) {
          Trace.debug("Key \"$newKey\" not found")
        }
      }
    } catch (e: JSONException) {
      e.printStackTrace()
    } catch (e: InstantiationException) {
      e.printStackTrace()
    } catch (e: IllegalAccessException) {
      e.printStackTrace()
    }
    
    return pClass as T
  }
}
