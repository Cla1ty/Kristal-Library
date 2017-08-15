package com.kristal.library.appbase.tools

import java.util.*

/**
 * Created by Kristal on 04/03/2017.
 */

object ProcessTime {
  private val hashMap = HashMap<String, Long>()
  
  fun register(pKey: String) {
    if (hashMap.containsKey(pKey)) {
      Trace.warm("KEY \"$pKey\" ALREADY REGISTERED", 1)
    }
    hashMap.put(pKey, System.currentTimeMillis())
  }
  
  fun getTime(pKey: String, pUnregister: Boolean) {
    if (!hashMap.containsKey(pKey)) {
      Trace.warm("KEY \"$pKey\" NOT FOUND", 1)
      return
    }
    
    var lastTime: Long = hashMap[pKey] ?: 0;
    Trace.warm("PROCESS TIME($pKey): " + (System.currentTimeMillis().minus(lastTime)) + " ms", 1)
    
    if (pUnregister) {
      hashMap.remove(pKey)
    }
  }
}
