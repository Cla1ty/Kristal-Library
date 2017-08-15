package com.kristal.library.appbase.recyclerview.data


import com.kristal.library.appbase.recyclerview.adapter.RecyclerViewData

/**
 * Created by Kristal on 7/12/2017.
 */

class IntData(val value: Int) : RecyclerViewData {
  
  override fun isSameId(pOtherData: RecyclerViewData): Boolean {
    return pOtherData is IntData && value == pOtherData.value
  }
  
  override fun isSameData(pOtherData: RecyclerViewData): Boolean {
    return isSameId(pOtherData)
  }
}
