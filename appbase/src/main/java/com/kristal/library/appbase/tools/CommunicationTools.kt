package com.kristal.library.appbase.tools

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View

/**
 * Created by Dwi on 7/28/2017.
 */

object CommunicationTools {
  fun callPhoneNumber(view: View, activity: Activity, phoneNumber: String?) {
    if (phoneNumber == null || phoneNumber.isEmpty() || phoneNumber == "0") return
    view.setOnClickListener {
      val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
      activity.startActivity(intent)
    }
  }
  
  fun sendEmail(view: View, activity: Activity, email: String?, subject: String, message: String) {
    Trace.warm("SEND: " + email!!)
    if (email == null || email.isEmpty() || email == "null") return
    Trace.warm("SENDING")
    view.setOnClickListener {
      Trace.warm("SEND: " + email)
      val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
      intent.putExtra(Intent.EXTRA_SUBJECT, subject)
      intent.putExtra(Intent.EXTRA_TEXT, message)
      activity.startActivity(intent)
    }
  }
}
