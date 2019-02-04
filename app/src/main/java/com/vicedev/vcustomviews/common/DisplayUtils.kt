package com.vicedev.vcustomviews.common

import android.content.Context

/**
 * Created by vice on 2019/2/4 10:39
 * E-mail:vicedev1001@gmail.com
 */

class DisplayUtils {

    companion object {
        /**
         * convert px to its equivalent dp
         *
         * 将px转换为与之相等的dp
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density;
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * convert dp to its equivalent px
         *
         * 将dp转换为与之相等的px
         */
        fun dp2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density;
            return (dipValue * scale + 0.5f).toInt()
        }

    }
}