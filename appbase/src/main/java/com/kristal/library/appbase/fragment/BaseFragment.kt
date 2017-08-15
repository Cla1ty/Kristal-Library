package com.kristal.library.appbase.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import com.kristal.library.appbase.activity.BaseActivity
import com.kristal.library.appbase.tools.Trace

/**
 * Created by Kristal on 2/5/2017.
 */
abstract class BaseFragment : Fragment() {
  protected var isTransitionComplete = false
  
  val setup = onSetup()
  var activity: BaseActivity? = null
  
  protected abstract fun onSetup(): Setup
  
  override fun onCreate(savedInstanceState: Bundle?) {
    Trace.debug("CREATE", this)
    super.onCreate(savedInstanceState)
    
    activity = if (getActivity() is BaseActivity) getActivity() as BaseActivity
    else null
  }
  
  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    Trace.debug("CREATE VIEW : ${setup.menuId != 0}", this)
    
    setHasOptionsMenu(setup.menuId != 0)
    return View.inflate(context, setup.layoutId, null)
  }
  
  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    Trace.debug("VIEW CREATED", this)
    
    val lActionBar = activity?.supportActionBar
    if (lActionBar != null) {
      if (setup.titleResId != 0) lActionBar.setTitle(setup.titleResId)
      else if (!setup.title.isEmpty()) lActionBar.title = setup.title
    }
  }
  
  override fun onStart() {
    Trace.debug("START", this)
    super.onStart()
  }
  
  override fun onResume() {
    Trace.debug("RESUME", this)
    super.onResume()
  }
  
  override fun onPause() {
    Trace.debug("PAUSE", this)
    super.onPause()
  }
  
  override fun onStop() {
    Trace.debug("STOP", this)
    super.onStop()
  }
  
  override fun onDestroy() {
    Trace.debug("DESTROY", this)
    super.onDestroy()
  }
  
  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    Trace.debug("CREATE OPTION MENU : $menu", this)
    inflater?.inflate(setup.menuId, menu)
  }
  
  override fun onSaveInstanceState(outState: Bundle?) {
    Trace.debug("SAVE INSTANCE STATE", this)
    super.onSaveInstanceState(outState)
  }
  
  override fun onHiddenChanged(hidden: Boolean) {
    Trace.debug("ON HIDDEN CHANGED: " + hidden, this)
    super.onHiddenChanged(hidden)
  }
  
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    Trace.debug("ACTIVITY RESULT", this)
    super.onActivityResult(requestCode, resultCode, data)
  }
  
  open fun onBackPressed(): Boolean {
    return false
  }
  
  inner class Setup(val tag: String, val layoutId: Int) {
    var titleResId = 0
    var title = ""
    var menuId = 0
  }
}
