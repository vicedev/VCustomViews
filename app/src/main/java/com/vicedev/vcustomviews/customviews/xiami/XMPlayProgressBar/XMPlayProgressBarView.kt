package com.vicedev.vcustomviews.customviews.xiami.XMPlayProgressBar

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Created by vice on 2019/2/28 23:32
 * E-mail:vicedev1001@gmail.com
 * 虾米音乐的音乐进度条
 */

class XMPlayProgressBarView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val xmPlayDragView: XMPlayDragView by lazy { XMPlayDragView(context) }

    init {
        xmPlayDragView.text="test/test"
        //添加拖拽的view
        addView(xmPlayDragView)
    }

}