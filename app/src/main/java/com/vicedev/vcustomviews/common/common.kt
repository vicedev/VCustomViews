package com.vicedev.vcustomviews.common

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.widget.Toast

/**
 * Created by vice on 2019/2/3 23:34
 * E-mail:vicedev1001@gmail.com
 *
 * 拓展函数
 */


fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

/**
 * 测量文字的rect
 */
fun Paint.getTextRect(text: String): Rect {
    return Rect().apply { getTextBounds(text, 0, text.length, this) }
}
