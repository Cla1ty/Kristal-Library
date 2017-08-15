package com.kristal.library.appbase.recyclerview.adapter

import android.databinding.ViewDataBinding

/**
 * Created by Kristal on 6/9/2017.
 */

class RecyclerViewLifeCycle<BINDING : ViewDataBinding, DATA : RecyclerViewData> {
  internal var create: ((RecyclerViewHolder<BINDING>) -> Unit)? = null
  internal var bind: ((RecyclerViewHolder<BINDING>, DATA) -> Unit)? = null
  internal var click: ((RecyclerViewHolder<BINDING>) -> Unit)? = null
  
  internal fun invokeCreate(holder: RecyclerViewHolder<*>) {
    create?.invoke(holder as RecyclerViewHolder<BINDING>)
  }
  
  internal fun invokeBind(holder: RecyclerViewHolder<*>, data: RecyclerViewData) {
    bind?.invoke(holder as RecyclerViewHolder<BINDING>, data as DATA)
  }
  
  internal fun invokeClick(holder: RecyclerViewHolder<*>) {
    click?.invoke(holder as RecyclerViewHolder<BINDING>)
  }
  
  fun onCreate(action: (RecyclerViewHolder<BINDING>) -> Unit): RecyclerViewLifeCycle<BINDING, DATA> {
    create = action
    return this
  }
  
  fun onBind(action: (RecyclerViewHolder<BINDING>, DATA) -> Unit): RecyclerViewLifeCycle<BINDING, DATA> {
    bind = action
    return this
  }
  
  fun onClick(action: (RecyclerViewHolder<BINDING>) -> Unit): RecyclerViewLifeCycle<BINDING, DATA> {
    click = action
    return this
  }
}