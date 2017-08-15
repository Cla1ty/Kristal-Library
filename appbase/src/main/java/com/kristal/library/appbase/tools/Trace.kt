package com.kristal.library.appbase.tools

import android.util.Log
import com.kristal.library.appbase.info.AppInfo

object Trace {
  private val trace = true//BuildConfig.BUILD_TYPE != "release";
  
  private fun buildString(pOutStrings: Array<String?>, pMessage: String, pParent: Int) {
    val lTraceElements = Throwable().stackTrace[2 + pParent]
    val lFileName = lTraceElements.fileName
    pOutStrings[0] = lFileName.substring(0, lFileName.length - 5)
    pOutStrings[1] = "." + lTraceElements.methodName + "(" + lTraceElements.fileName + ":" + lTraceElements.lineNumber + ")" + (if (pMessage == "") ""
    else " ") + pMessage
  }
  
  fun root() {
    val lTraceElements = Throwable().stackTrace
    var lFileName = lTraceElements[1].fileName
    lFileName = lFileName.substring(0, lFileName.length - 5)
    for (i in 1..lTraceElements.size - 1) {
      if (!lTraceElements[i].className.contains(AppInfo.packageName)) return
      Log.e(lFileName, "" + lTraceElements[i])
    }
  }
  
  fun verbose(pTitle: String, vararg pMessage: String) {
    var lMessage = "===== " + pTitle.toUpperCase() + " ====="
    for (s in pMessage) {
      lMessage += "\n" + s
    }
    lMessage += "\n===== end ====="
    verbose(lMessage, 1)
  }
  
  fun verbose(pMessage: String) {
    verbose(pMessage, 1)
  }
  
  fun verbose(pMessage: String, pParent: Int) {
    if (!trace) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pMessage, pParent)
    Log.v(strings[0], strings[1])
  }
  
  fun debug(pMessage: String, pContext: Any) {
    debug(pMessage + " --> " + pContext.javaClass.simpleName, 1)
  }
  
  fun debug(pMessage: String) {
    debug(pMessage, 1)
  }
  
  fun debug(pMessage: String, pParent: Int) {
    if (!trace) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pMessage, pParent)
    Log.d(strings[0], strings[1])
  }
  
  fun info(pTitle: String, vararg pMessage: String) {
    var lMessage = "===== " + pTitle.toUpperCase() + " ====="
    for (s in pMessage) {
      lMessage += "\n" + s
    }
    lMessage += "\n===== end ====="
    info(lMessage, 1)
  }
  
  fun info(obj: Any) {
    var lMessage = "===== " + obj.javaClass.simpleName.toUpperCase() + " ====="
    try {
      for (lField in obj.javaClass.declaredFields) {
        if (lField.name == "\$change" || lField.name == "serialVersionUID") continue
        
        lField.isAccessible = true
        lMessage += "\n" + lField.name + " : " + lField.get(obj)
      }
    } catch (e: IllegalAccessException) {
      e.printStackTrace()
    }
    
    lMessage += "\n===== end ====="
    info(lMessage, 1)
  }
  
  fun info(pClass: Class<*>) {
    var lMessage = "===== " + pClass.simpleName.toUpperCase() + " ====="
    try {
      for (lField in pClass.declaredFields) {
        if (lField.name == "\$change" || lField.name == "serialVersionUID") continue
        
        lField.isAccessible = true
        lMessage += "\n" + lField.name + " : " + lField.get(null)
      }
    } catch (e: IllegalAccessException) {
      e.printStackTrace()
    }
    
    lMessage += "\n===== end ====="
    info(lMessage, 1)
  }
  
  fun info(pMessage: String) {
    info(pMessage, 1)
  }
  
  fun info(pMessage: String, pParent: Int) {
    if (!trace) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pMessage, pParent)
    Log.i(strings[0], strings[1])
  }
  
  fun warm(pMessage: String) {
    warm(pMessage, 1)
  }
  
  fun warm(pMessage: String, pParent: Int) {
    if (!trace) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pMessage, pParent)
    Log.w(strings[0], strings[1])
  }
  
  fun error(pMessage: String) {
    error(pMessage, 1)
  }
  
  fun error(pMessage: String, pParent: Int) {
    if (!trace) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pMessage, pParent)
    Log.e(strings[0], strings[1])
  }
  
  fun wtf(pMessage: String) {
    wtf(pMessage, 1)
  }
  
  private fun wtf(pMessage: String, pParent: Int) {
    if (!trace) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pMessage, pParent)
    Log.wtf(strings[0], strings[1])
  }
  
  fun wtf(pVariableName: String, pValue: Int, pValueNeed: Int) {
    if (!trace || pValue == pValueNeed) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, "$pVariableName expected $pValueNeed but was $pValue", 0)
    Log.wtf(strings[0], strings[1])
  }
  
  fun wtfNot(pVariableName: String, pValue: Int, pValueNeed: Int) {
    if (!trace || pValue == pValueNeed) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pVariableName + " expected not " + pValueNeed, 0)
    Log.wtf(strings[0], strings[1])
  }
  
  fun wtf(pVariableName: String, pValue: Boolean, pValueNeed: Boolean) {
    if (!trace || pValue == pValueNeed) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, "$pVariableName expected $pValueNeed but was $pValue", 0)
    Log.wtf(strings[0], strings[1])
  }
  
  fun wtf(pVariableName: String, pValue: String, pValueNeed: String) {
    if (!trace || pValue == pValueNeed) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, "$pVariableName expected $pValueNeed but was $pValue", 0)
    Log.wtf(strings[0], strings[1])
  }
  
  fun wtfNot(pVariableName: String, pValue: String, pValueNeed: String) {
    if (!trace || pValue != pValueNeed) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, "$pVariableName expected not $pValueNeed but was $pValue", 0)
    Log.wtf(strings[0], strings[1])
  }
  
  fun wtf(pVariableName: String, pValue: Any?) {
    if (!trace || pValue != null) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pVariableName + " expected NOT NULL but was NULL", 0)
    Log.wtf(strings[0], strings[1])
  }
  
  fun wtf(pValue: Any?, pMessage: String) {
    if (!trace || pValue != null) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pMessage, 0)
    Log.wtf(strings[0], strings[1])
  }
  
  fun wtf(pValue: Boolean, pMessage: String) {
    if (!trace || pValue) return
    
    val strings = arrayOfNulls<String>(2)
    buildString(strings, pMessage, 0)
    Log.wtf(strings[0], strings[1])
  }
  
  fun newLine() {
    Log.w("New Line", "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n")
  }
}
