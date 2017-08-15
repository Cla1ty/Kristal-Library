package com.kristal.library.appbase.tools

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat

/**
 * Created by Dwi on 7/3/2017.
 */

object LocationTools {
  var location: Location? = null
    private set
  
  fun with(pContext: Context) {
    requestLocation(pContext, null)
  }
  
  fun requestLocation(pContext: Context, pListenr: OnLocationChangedListener?) {
    val lm = pContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (ActivityCompat.checkSelfPermission(pContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(pContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      return
    }
    
    var gps_enabled = false
    var network_enabled = false
    try {
      gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (ex: Exception) {
    }
    
    try {
      network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (ex: Exception) {
    }
    
    Trace.warm("CEK : $gps_enabled, $network_enabled")
    //don't start listeners if no provider is enabled
    if (!gps_enabled && !network_enabled) return
    
    val locationListenerGps = object : LocationListener {
      override fun onLocationChanged(pLocation: Location) {
        Trace.warm("Location Change : " + pLocation)
        lm.removeUpdates(this)
        location = pLocation
        
        pListenr?.onLocationChanged(pLocation)
      }
      
      override fun onProviderDisabled(provider: String) {
        Trace.warm("Provider Disabled : " + provider)
      }
      
      override fun onProviderEnabled(provider: String) {
        Trace.warm("Provider Enabled : " + provider)
      }
      
      override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        Trace.warm("Status Changed : $provider, status: $status")
      }
    }
    if (gps_enabled)
      lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListenerGps)
    if (network_enabled)
      lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListenerGps)
  }
  
  interface OnLocationChangedListener {
    fun onLocationChanged(pLocation: Location)
  }
}
