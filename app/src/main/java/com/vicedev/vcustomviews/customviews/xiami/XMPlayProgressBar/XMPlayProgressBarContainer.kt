package com.vicedev.vcustomviews.customviews.xiami.XMPlayProgressBar

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.xiami_play_progressbar_container.view.*

/**
 * Created by vice on 2019/2/28 23:44
 * E-mail:vicedev1001@gmail.com
 */

class XMPlayProgressBarContainer @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        xm_pbv.setTotal(360)
        xm_pbv.setCurrent(0)
        btn_set.setOnClickListener {
            val current = et_current.text.toString()
            val total = et_total.text.toString()
            xm_pbv.setCurrent( if (TextUtils.isEmpty(current)) 0 else current.toInt())
            xm_pbv.setTotal( if (TextUtils.isEmpty(total)) 0 else total.toInt())
        }
    }
}