package com.vicedev.vcustomviews.customviews.xiami.XMPlayProgressBar

import android.content.Context
import android.util.AttributeSet
import android.view.View
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

    private val centerWidth by lazy { DisplayUtils.dp2px(context, 100.0f) }
    private val lrWidth by lazy { DisplayUtils.dp2px(context, 120.0f) }

    var currentMode = MODE_HIDE

    companion object {
        const val MODE_LEFT = 1//往左
        const val MODE_RIGHT = 2//往右
        const val MODE_CENTER = 3//中间
        const val MODE_HIDE = 4//消失
    }

    init {
        visibility = View.INVISIBLE
    }

    fun setDisplayMode(mode: Int): XMPlayLabelView {

        if (mode == currentMode) {
            return this
        } else {
            currentMode = mode
        }

        when (mode) {
            MODE_CENTER -> {
                width = centerWidth
                setBackgroundResource(R.drawable.xiami_label_view_bg_center)
                visibility = VISIBLE
            }

            MODE_LEFT -> {
                width = lrWidth
                setBackgroundResource(R.drawable.xiami_label_view_bg_left)
                visibility = VISIBLE
            }

            MODE_RIGHT -> {
                width = lrWidth
                setBackgroundResource(R.drawable.xiami_label_view_bg_right)
                visibility = VISIBLE
            }

            MODE_HIDE -> visibility = INVISIBLE
        }
        return this
    }

    /**
     * 获取相应mode下的宽度
     */
    fun getCurrentWidth(): Int {
        return when (currentMode) {
            MODE_CENTER -> centerWidth
            MODE_LEFT, MODE_RIGHT -> lrWidth
            else -> centerWidth
        }
    }
}