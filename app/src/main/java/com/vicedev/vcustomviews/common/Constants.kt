package com.vicedev.vcustomviews.common

import android.support.v4.util.ArrayMap


/**
 * Created by vice on 2019/2/3 21:31
 * E-mail:vicedev1001@gmail.com
 */

class Constants {
    companion object {
        var APP_MAP = ArrayMap<String, String>()

        init {
            APP_MAP.put("GET", "get")
        }
    }
}