package com.vicedev.vcustomviews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vicedev.vcustomviews.common.showToast

class AppDetailListActivity : AppCompatActivity() {

    companion object {
        val APP_NAME = "app_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_detail_list)

        val appName = intent.getStringExtra(APP_NAME)
        showToast(appName)
    }
}
