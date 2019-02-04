package com.vicedev.vcustomviews.common

import android.support.v4.util.ArrayMap


/**
 * Created by vice on 2019/2/3 21:31
 * E-mail:vicedev1001@gmail.com
 *
 * 常量
 */

class Constants {
    companion object {

        //get
        val APP_GET = "GET"
        val GET_CUSTOM_PUBLISH = "发布内容弹出效果"

        val APP_MAP = ArrayMap<String, ArrayList<String>>()

        init {
            with(APP_MAP) {
                put(APP_GET, arrayListOf(GET_CUSTOM_PUBLISH))
            }
        }

    }
}