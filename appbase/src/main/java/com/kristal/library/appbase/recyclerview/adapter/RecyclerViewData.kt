package com.kristal.library.appbase.recyclerview.adapter

/**
 * Created by Kristal on 6/2/2017.
 */

interface RecyclerViewData {
  fun isSameId(otherData: RecyclerViewData): Boolean
  fun isSameData(otherData: RecyclerViewData): Boolean
}