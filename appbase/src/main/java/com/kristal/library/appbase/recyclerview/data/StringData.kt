package com.kristal.library.appbase.recyclerview.data


import com.kristal.library.appbase.recyclerview.adapter.RecyclerViewData

/**
 * Created by Kristal on 7/11/2017.
 */

class StringData(val string: String) : RecyclerViewData {
  
  override fun isSameId(pOtherData: RecyclerViewData): Boolean {
    return pOtherData is StringData && pOtherData.string == string
  }
  
  override fun isSameData(pOtherData: RecyclerViewData): Boolean {
    return isSameId(pOtherData)
  }
}
