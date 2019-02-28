package com.vicedev.vcustomviews.customviews.xiami.XMPlayProgressBar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.vicedev.vcustomviews.R
import com.vicedev.vcustomviews.common.DisplayUtils

/**
 * Created by vice on 2019/2/28 23:21
 * E-mail:vicedev1001@gmail.com
 *
 * 拖拽和显示的view
 */

class XMPlayDragView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
    init {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10.0f)
        setTextColor(Color.WHITE)
        setPadding(DisplayUtils.dp2px(context, 10.0f), DisplayUtils.dp2px(context, 5.0f),
                DisplayUtils.dp2px(context, 10.0f), DisplayUtils.dp2px(context, 5.0f))
        setBackgroundResource(R.drawable.xiami_drag_view_bg)
    }
}