package com.kristal.library.appbase.tools

import android.content.Context
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.NoConnectionError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kristal.library.appbase.R
import org.json.JSONObject
import java.util.*

/**
 * Created by Kristal on 6/20/2017.
 */

object VolleyTools {
  private var queue: RequestQueue? = null
  
  fun init(pContext: Context) {
    queue = Volley.newRequestQueue(pContext)
  }
  
  fun addQueue(pRequest: Request<*>) {
    queue!!.add(pRequest)
  }
  
  fun get(pContext: Context, pUrl: String, pListener: ParameterListener?, pSuccess: Response.Listener<String>?, pError: Response.ErrorListener?) {
    return request(pContext, Request.Method.GET, pUrl, pListener, pSuccess, pError)
  }
  
  fun post(pContext: Context, pUrl: String, pListener: ParameterListener?, pSuccess: Response.Listener<String>?, pError: Response.ErrorListener?) {
    return request(pContext, Request.Method.POST, pUrl, pListener, pSuccess, pError)
  }
  
  fun request(pContext: Context, method: Int, pUrl: String, pListener: ParameterListener?, pSuccess: Response.Listener<String>?, pError: Response.ErrorListener?) {
    Trace.info("URL: " + pUrl)
    
    val stringRequest = object : StringRequest(method, pUrl, Response.Listener {
      Trace.info("RESPONSE $it")
      pSuccess?.onResponse(it)
    }, Response.ErrorListener { error ->
      Trace.info("RESPONSE " + error)
      
      var lMessage = "Error"
      if (error is NoConnectionError) lMessage = pContext.resources.getString(R.string.no_connection_message)
      
      pError?.onErrorResponse(error)
      
      Toast.makeText(pContext, lMessage, Toast.LENGTH_LONG).show()
    }) {
      @Throws(AuthFailureError::class) override fun getParams(): Map<String, String> {
        val params = HashMap<String, String>()
        pListener?.setParameter(params)
        
        var lString = ""
        for ((key, value) in params) lString += "\n" + key + " = " + value
        Trace.info("POST" + lString)
        
        return params
      }
    }
    addQueue(stringRequest)
  }
  
  fun getJson(pContext: Context, pUrl: String, pListener: ParameterListener?, pSuccess: Response.Listener<JSONObject>?, pError: Response.ErrorListener?) {
    return requestJson(pContext, Request.Method.GET, pUrl, pListener, pSuccess, pError)
  }
  
  fun requestJson(pContext: Context, method: Int, pUrl: String, pListener: ParameterListener?, pSuccess: Response.Listener<JSONObject>?, pError: Response.ErrorListener?) {
    Trace.info("URL: " + pUrl)
    
    val stringRequest = object : JsonObjectRequest(method, pUrl, null, Response.Listener {
      Trace.info("RESPONSE $it")
      pSuccess?.onResponse(it)
    }, Response.ErrorListener { error ->
      Trace.info("RESPONSE " + error)
      
      var lMessage = "Error"
      if (error is NoConnectionError) lMessage = pContext.resources.getString(R.string.no_connection_message)
      
      pError?.onErrorResponse(error)
      
      Toast.makeText(pContext, lMessage, Toast.LENGTH_LONG).show()
    }) {
      @Throws(AuthFailureError::class) override fun getParams(): Map<String, String> {
        val params = HashMap<String, String>()
        pListener?.setParameter(params)
        
        var lString = ""
        for ((key, value) in params) lString += "\n" + key + " = " + value
        Trace.info("POST" + lString)
        
        return params
      }
    }
    addQueue(stringRequest)
  }
  
  fun futureGet(context: Context, url: String, pListener: ParameterListener?): String {
    val future = RequestFuture.newFuture<String>()
    get(context, url, pListener, future, future)
    return future.get()
  }
  
  fun futurePost(context: Context, url: String, pListener: ParameterListener?): RequestFuture<String> {
    val future = RequestFuture.newFuture<String>()
    post(context, url, pListener, future, future)
    return future
  }
  
  interface ParameterListener {
    fun setParameter(pParameter: Map<String, String>)
  }
  
  interface OnResponseListener {
    fun onResponse(pStatus: Int, pResponse: String)
  }
}
