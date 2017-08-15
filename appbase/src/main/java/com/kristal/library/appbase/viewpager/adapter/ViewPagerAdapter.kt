package com.kristal.library.appbase.viewpager.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by USER on 3/11/2017.
 */

class ViewPagerAdapter : PagerAdapter() {
  private val data = ArrayList<Entity>()
  
  override fun getCount() = data.size
  
  override fun isViewFromObject(view: View, `object`: Any) = view === `object`
  
  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val lView = data[position].view
    container.addView(lView)
    return lView
  }
  
  override fun getPageTitle(position: Int) = data[position].title
  
  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    container.removeView(`object` as View)
  }
  
  fun addPage(pTitle: String, pPage: View) {
    data.add(Entity(pTitle, pPage))
  }
  
  inner class Entity(val title: String, val view: View)
}
