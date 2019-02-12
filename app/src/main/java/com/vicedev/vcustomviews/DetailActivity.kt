package com.vicedev.vcustomviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.vicedev.vcustomviews.common.Constants
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val CUSTOM_VIEW_NAME = "custom_view_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val appName = intent.getStringExtra(AppDetailListActivity.APP_NAME)
        val customViewName = intent.getStringExtra(CUSTOM_VIEW_NAME)

        //标题栏
        supportActionBar?.title = customViewName

        //自定义view的layout
        var viewId = 0

        when (appName) {
            //get
            Constants.APP_GET -> when (customViewName) {
                //发布内容弹出效果
                Constants.GET_CUSTOM_PUBLISH -> {
                    viewId = R.layout.get_publish_view
                }
            }
            //马蜂窝
            Constants.APP_MFW -> when (customViewName) {
                //蠕动tab
                Constants.MFW_WRIGGLE_TAB -> {
                    viewId = R.layout.mfw_wriggle_tab_view
                }
            }

            //支付宝
            Constants.APP_ZFB -> when (customViewName) {
                //芝麻信用
                Constants.ZFB_SESAME_CREDIT -> {
                    viewId = R.layout.zfb_sesame_credit_container
                }
            }

            //微信
            Constants.APP_WE_CHAT -> when (customViewName) {
                //芝麻信用
                Constants.WE_CHAT_RADAR_ADD_FRIENDS -> {
                    supportActionBar?.hide()
                    viewId = R.layout.wechat_rader_add_friends_view
                }
            }
        }
        //添加到当前布局中
        if (viewId != 0) View.inflate(this@DetailActivity, viewId, container)
    }
}
