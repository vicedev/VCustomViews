package com.vicedev.vcustomviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem

/**
 * Created by vice on 2019/2/13 22:45
 * E-mail:vicedev1001@gmail.com
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.run {
            //显示标题栏返回按钮
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //点击标题栏返回按钮的事件
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //隐藏标题栏
    fun hideTitleBar() {
        supportActionBar?.hide()
    }

    //设置标题
    fun setTitle(title: String?) {
        if (!TextUtils.isEmpty(title)) {
            supportActionBar?.title = title
        }
    }

    //隐藏返回按钮
    fun hideTitleBarBackBtn() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}