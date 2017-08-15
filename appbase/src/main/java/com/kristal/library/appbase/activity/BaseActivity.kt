package com.kristal.library.appbase.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.transition.Fade
import android.view.View
import android.widget.Toast
import com.kristal.library.appbase.R
import com.kristal.library.appbase.activity.shareelement.ShareElementTransition
import com.kristal.library.appbase.extensions.className
import com.kristal.library.appbase.fragment.BaseFragment
import com.kristal.library.appbase.tools.Trace

/**
 * Created by Kristal on 2/5/2017.
 */
abstract class BaseActivity : PermissionActivity() {
  private var isStartIntent: Boolean = false
  private var doubleBack: Boolean = false
  
  private val setup: Setup = onSetup()
  
  protected abstract fun onSetup(): Setup
  protected fun onRequestFeature() {}
  
  override fun onCreate(savedInstanceState: Bundle?) {
    Trace.debug("CREATE", this)
    onRequestFeature()
    super.onCreate(savedInstanceState)
    
    Trace.newLine()
    
    overridePendingTransition(setup.animationEnterIntent, setup.animationExitIntent)
    if (setup.layoutId != DEFAULT_LAYOUT_ID) setContentView(setup.layoutId)
  }
  
  override fun onStart() {
    Trace.debug("onStart", this)
    super.onStart()
    
    val enter = if (isStartIntent) setup.animationIntentPopEnter
    else setup.animationEnterIntent
    val exit = if (isStartIntent) setup.animationIntentPopExit
    else setup.animationExitIntent
    
    overridePendingTransition(enter, exit)
  }
  
  override fun onResume() {
    Trace.debug("RESUME", this)
    super.onResume()
  }
  
  override fun onPause() {
    Trace.debug("PAUSE", this)
    
    super.onPause()
    
    val enter = if (isStartIntent) setup.animationEnterIntent
    else setup.animationIntentPopEnter
    val exit = if (isStartIntent) setup.animationExitIntent
    else setup.animationIntentPopExit
    
    overridePendingTransition(enter, exit)
  }
  
  override fun onStop() {
    Trace.debug("STOP", this)
    super.onStop()
  }
  
  override fun onDestroy() {
    Trace.debug("DESTROY", this)
    super.onDestroy()
  }
  
  override fun finish() {
    Trace.debug("FINISH", this)
    
    super.finish()
    overridePendingTransition(setup.animationIntentPopEnter, setup.animationIntentPopExit)
  }
  
  override fun startActivityForResult(intent: Intent, requestCode: Int) {
    super.startActivityForResult(intent, requestCode)
    Trace.info("INTENT: $intent, $requestCode")
    isStartIntent = true
    
  }
  
  override fun onSaveInstanceState(outState: Bundle) {
    Trace.debug("Save Instance State", this)
    super.onSaveInstanceState(outState)
  }
  
  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    Trace.debug("Restore Instance State", this)
    super.onRestoreInstanceState(savedInstanceState)
  }
  
  override fun onBackPressed() {
    Trace.newLine()
    Trace.debug("BACK FRAGMENT PRESS", this)
    
    if (currentFragment != null && (currentFragment as BaseFragment).onBackPressed()) return
    
    Trace.debug("BACK PREVIEWS FRAGMENT", this)
    if (backToPreviousFragment()) return
    
    Trace.debug("BACK PRESS", this)
    super.onBackPressed()
  }
  
  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
  
  fun onDoubleBackPressed(pMessage: String = "EXIT"): Boolean {
    if (doubleBack) return false
    
    doubleBack = true
    Toast.makeText(this, "Please menuClick BACK again to " + pMessage, Toast.LENGTH_SHORT).show()
    Handler().postDelayed({ doubleBack = false }, 2000)
    
    return true
  }
  
  fun showFragment(pBaseFragment: BaseFragment, pAddToBackStack: Boolean = true, pShareElement: View? = null, pShareName: String = "") {
    Trace.newLine()
    Trace.info("SHOW FRAGMENT: " + pBaseFragment.className)
    
    val lFragmentTransaction = supportFragmentManager.beginTransaction()
    if (currentFragment != null) lFragmentTransaction.setCustomAnimations(setup.animationEnter, setup.animationExit, setup.animationPopEnter, setup.animationPopExit);
    lFragmentTransaction.add(setup.containerId, pBaseFragment, pBaseFragment.setup.tag);
    
    if (pShareElement != null && !pShareName.isEmpty()) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        pBaseFragment.sharedElementEnterTransition = ShareElementTransition()
        pBaseFragment.enterTransition = Fade()
        currentFragment?.exitTransition = Fade()
        pBaseFragment.sharedElementReturnTransition = ShareElementTransition()
      }
      lFragmentTransaction.addSharedElement(pShareElement, pShareName)
    }
    
    if (pAddToBackStack) lFragmentTransaction.addToBackStack(pBaseFragment.setup.tag).commit()
    else lFragmentTransaction.commitNow()
    
    Trace.warm("POP Count: " + supportFragmentManager.backStackEntryCount)
  }
  
  val currentFragment: Fragment?
    get() = supportFragmentManager.findFragmentById(setup.containerId)
  
  private fun backToPreviousFragment(): Boolean {
    val lFragmentManager = supportFragmentManager
    lFragmentManager.popBackStackImmediate()
    if (currentFragment != null && currentFragment is BaseFragment) supportActionBar?.title = (currentFragment as BaseFragment).setup.title
    return supportFragmentManager.backStackEntryCount != 0
  }
  
  inner class Setup {
    var layoutId = DEFAULT_LAYOUT_ID
    var containerId = DEFAULT_CONTAINER_ID
    
    var animationEnterIntent = R.anim.slide_right_in
    var animationExitIntent = R.anim.slide_left_out
    var animationIntentPopEnter = R.anim.slide_left_in
    var animationIntentPopExit = R.anim.slide_right_out
    
    var animationEnter = R.anim.slide_right_in
    var animationExit = R.anim.slide_left_out
    var animationPopEnter = R.anim.slide_left_in
    var animationPopExit = R.anim.slide_right_out
  }
  
  companion object {
    protected val DEFAULT_LAYOUT_ID = -1
    protected val DEFAULT_CONTAINER_ID = android.R.id.content
  }
}
