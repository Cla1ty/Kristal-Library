package com.kristal.library.appbase.tools.video

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever

/**
 * Created by USER on 11/28/2016.
 */

object MediaMetaTools {
  fun getRetriever(pPath: String): MediaMetadataRetriever {
    val lRetriever = MediaMetadataRetriever()
    lRetriever.setDataSource(pPath)
    return lRetriever
  }
  
  fun getDuration(pRetriever: MediaMetadataRetriever): Float {
    val lVideoLength = pRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
    return Integer.parseInt(lVideoLength) / 1000f
  }
  
  fun getFrame(pRetriever: MediaMetadataRetriever, pTime: Float): Bitmap {
    val lTime = (pTime * 1000000).toInt().toLong()
    return pRetriever.getFrameAtTime(lTime)
  }
  
  fun getTitle(pRetriever: MediaMetadataRetriever) = pRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
  
  fun getArtist(pRetriever: MediaMetadataRetriever) = pRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
  
  fun getCover(pRetriever: MediaMetadataRetriever): Bitmap? {
    val coverBytes = pRetriever.embeddedPicture
    if (coverBytes == null)
      return null
    else
      return BitmapFactory.decodeByteArray(coverBytes, 0, coverBytes.size)
  }
}
