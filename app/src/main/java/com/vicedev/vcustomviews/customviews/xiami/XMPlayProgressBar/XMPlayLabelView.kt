package com.vicedev.vcustomviews.customviews.xiami.XMPlayProgressBar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vicedev.vcustomviews.R
import com.vicedev.vcustomviews.common.DisplayUtils

/**
 * Created by vice on 2019/3/1 22:10
 * E-mail:vicedev1001@gmail.com
 *
 * 拖拽view上方用于展示的view
 */

class XMPlayLabelView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private val padding1 by lazy { DisplayUtils.dp2px(context, 5.0f) }
    private val padding2 by lazy { DisplayUtils.dp2px(context, 10.0f) }
    private val padding3 by lazy { DisplayUtils.dp2px(context, 20.0f) }

    private var currentMode = MODE_HIDE

    companion object {
        const val MODE_LEFT = 1//往左
        const val MODE_RIGHT = 2//往右
        const val MODE_CENTER = 3//中间
        const val MODE_HIDE = 4//消失
    }

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setPadding(padding2, padding1, padding2, padding1)
        visibility = View.INVISIBLE
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15.0f)
        setTextColor(Color.WHITE)
    }

    fun setDisplayMode(mode: Int): XMPlayLabelView {

        if (mode == currentMode) {
            return this
        } else {
            currentMode = mode
        }

        when (mode) {
            MODE_CENTER -> {
                setPadding(padding2, padding1, padding2, padding1)
                setBackgroundResource(R.drawable.xiami_label_view_bg_center)
                visibility = VISIBLE
            }

            MODE_LEFT -> {
                setPadding(padding3, padding1, padding3, padding1)
                setBackgroundResource(R.drawable.xiami_label_view_bg_left)
                visibility = VISIBLE
            }

            MODE_RIGHT -> {
                setPadding(padding3, padding1, padding3, padding1)
                setBackgroundResource(R.drawable.xiami_label_view_bg_right)
                visibility = VISIBLE
            }

            MODE_HIDE -> visibility = INVISIBLE
        }
        return this
    }
}