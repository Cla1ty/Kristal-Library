package com.kristal.library.appbase.recyclerview.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kristal.library.appbase.recyclerview.GenericTools
import com.kristal.library.appbase.recyclerview.snaphelper.GravitySnapHelper
import com.kristal.library.appbase.tools.Trace

/**
 * Created by Kristal on 6/8/2017.
 */

// todo pisah adapter dengan data binding dan tanpa data binding
class RecyclerViewAdapter(private var data: List<RecyclerViewData>?) : RecyclerView.Adapter<RecyclerViewHolder<*>>() {
  init {
    if (data != null) notifyItemRangeInserted(0, data!!.size)
  }
  
  private var keys = ArrayList<HolderData>()
  private var snapHelper: SnapHelper? = null
  
  override fun onBindViewHolder(p0: RecyclerViewHolder<*>, p1: Int) {
    val id = getId(data!![p1].javaClass)
    keys[id].lifeCycle.invokeBind(p0, data!![p1])
  }
  
  override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): RecyclerViewHolder<*> {
    val key = keys[p1]
    val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(p0!!.context), key.layoutId, p0, false)
    val holder = RecyclerViewHolder<ViewDataBinding>(binding)
    key.lifeCycle.invokeCreate(holder)
    if (key.lifeCycle.click != null) {
      binding.root.setOnClickListener {
        key.lifeCycle.invokeClick(holder)
      }
    }
    return holder
  }
  
  override fun getItemCount() = data?.size ?: 0
  
  override fun getItemViewType(position: Int): Int {
    val cls = data!![position].javaClass
    return getId(cls)
  }
  
  fun getId(cls: Class<*>): Int {
    val id = cls.canonicalName
    
    for (index in keys.indices) {
      if (keys[index].id == id) {
        return index
      } else {
        return getId(cls.superclass)
      }
    }
    
    return -1
  }
  
  fun <BINDING : ViewDataBinding, DATA : RecyclerViewData> register(layoutId: Int, callback: (RecyclerViewLifeCycle<BINDING, DATA>) -> Unit): RecyclerViewAdapter {
    val id = GenericTools.getClassNameFromLamda(callback.javaClass, 1)
    val lifeCycle = RecyclerViewLifeCycle<BINDING, DATA>()
    callback.invoke(lifeCycle)
    keys.add(HolderData(id, layoutId, lifeCycle))
    
    return this
  }
  
  fun enableSnapHelper(gravity: Int): RecyclerViewAdapter {
    snapHelper = when (gravity) {
      Gravity.CENTER -> LinearSnapHelper()
      else -> GravitySnapHelper(gravity)
    }
    
    return this
  }
  
  fun updateData(newData: List<RecyclerViewData>?) {
    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
      override fun getOldListSize() = data?.size ?: 0
      override fun getNewListSize() = newData?.size ?: 0
      override fun areItemsTheSame(p0: Int, p1: Int) = data!![p0].isSameId(newData!![p1])
      override fun areContentsTheSame(p0: Int, p1: Int) = data!![p0].isSameData(newData!![p1])
    }).also {
      this.data = newData
      it.dispatchUpdatesTo(this)
    }
  }
  
  fun apply(vararg recyclerView: RecyclerView) {
    for (view in recyclerView) {
      snapHelper?.attachToRecyclerView(view)
      view.adapter = this
    }
  }
  
  internal class HolderData(val id: String, val layoutId: Int, val lifeCycle: RecyclerViewLifeCycle<*, *>) {
    init {
      Trace.info(this)
    }
  }
}
