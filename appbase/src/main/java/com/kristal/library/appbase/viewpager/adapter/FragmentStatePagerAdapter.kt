package com.kristal.library.appbase.viewpager.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import java.util.*

/**
 * Created by USER on 5/22/2017.
 */

class FragmentStatePagerAdapter(fm: FragmentManager) : android.support.v4.app.FragmentStatePagerAdapter(fm) {
  private val data = ArrayList<Entity>()
  
  override fun getItem(i: Int) = data[i].fragment
  
  override fun getCount() = data.size
  
  fun addPage(pTitle: String, pFragment: Fragment) {
    data.add(Entity(pTitle, pFragment))
  }
  
  inner class Entity(val title: String, val fragment: Fragment)
}
