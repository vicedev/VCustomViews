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
        const val APP_GET = "GET"
        const val GET_CUSTOM_PUBLISH = "发布内容弹出效果"

        //马蜂窝旅游
        const val APP_MFW = "马蜂窝旅游"
        const val MFW_WRIGGLE_TAB = "蠕动tab"

        //支付宝
        const val APP_ZFB = "支付宝"
        const val ZFB_SESAME_CREDIT = "芝麻信用"

        val APP_MAP = ArrayMap<String, ArrayList<String>>()

        init {
            with(APP_MAP) {
                put(APP_GET, arrayListOf(GET_CUSTOM_PUBLISH))
                put(APP_MFW, arrayListOf(MFW_WRIGGLE_TAB))
                put(APP_ZFB, arrayListOf(ZFB_SESAME_CREDIT))
            }
        }

    }
}