package com.vicedev.vcustomviews.common

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * Created by vice on 2019/2/12 22:51
 * E-mail:vicedev1001@gmail.com
 */
class BitmapUtils {
    companion object {
        fun readBitmapFromResource(resources: Resources, resId: Int, width: Int, height: Int): Bitmap {
            val options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, resId, options)
            val srcWidth = options.outWidth
            val srcHeight = options.outHeight
            var inSampleSize = 1

            if (srcWidth > height && srcWidth > width) {
                inSampleSize = srcWidth / width
            } else if (srcWidth < height && srcHeight > height) {
                inSampleSize = srcHeight / height
            }

            if (inSampleSize <= 0) {
                inSampleSize = 1
            }
            options.inJustDecodeBounds = false
            options.inSampleSize = inSampleSize

            return BitmapFactory.decodeResource(resources, resId, options)
        }
    }
}