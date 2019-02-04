package com.vicedev.vcustomviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.vicedev.vcustomviews.common.Constants
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        val CUSTOM_VIEW_NAME = "custom_view_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val appName = intent.getStringExtra(AppDetailListActivity.APP_NAME)
        val customViewName = intent.getStringExtra(CUSTOM_VIEW_NAME)

        //标题栏
        supportActionBar?.title = customViewName

        when (appName) {
        //get
            Constants.APP_GET -> when (customViewName) {
            //发布内容弹出效果
                Constants.GET_CUSTOM_PUBLISH -> {
                    View.inflate(this@DetailActivity, R.layout.get_publish_view, container)
                }
            }
        }
    }
}
