package com.vicedev.vcustomviews.customviews.xiami.XMPlayProgressBar

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.vicedev.vcustomviews.common.DisplayUtils

/**
 * Created by vice on 2019/2/28 23:32
 * E-mail:vicedev1001@gmail.com
 * 虾米音乐的音乐进度条
 */

class XMPlayProgressBarView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val xmPlayDragView: XMPlayDragView by lazy { XMPlayDragView(context) }
    private val xmPlayLabelView: XMPlayLabelView by lazy { XMPlayLabelView(context) }

    //总时间
    private var total = 360
        set(value) {
            if (value <= 0) return
            field = value
            postInvalidate()
        }

    //当前时间
    private var current = 0
        set(value) {
            field = if (value > total) total else value
            postInvalidate()
        }

    init {
        orientation = VERTICAL
        setWillNotDraw(false)

        xmPlayDragView.text = currentLabel()

        //添加上方显示的LabelView
        addView(xmPlayLabelView)
        //添加拖拽的view
        addView(xmPlayDragView)

        //隔一点间距
        (xmPlayDragView.layoutParams as MarginLayoutParams).bottomMargin = DisplayUtils.dp2px(context, 10.0f)
        xmPlayDragView.requestLayout()

        xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_LEFT).text = currentLabel()

        xmPlayDragView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_CENTER)
                }

                MotionEvent.ACTION_MOVE -> {

                }

                MotionEvent.ACTION_UP -> {

                    xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_HIDE)
                }
            }
            true
        }
    }

    /**
     * 当前显示：0:00/6:00
     */
    private fun currentLabel(): String = "${formatTime(current)}/${formatTime(total)}"

    /**
     * （秒）转（分：秒）
     */
    private fun formatTime(second: Int): String {
        val m = second / 60
        var s = (second % 60).toString()
        if (s == "0") {
            s = "00"
        }
        return "$m:$s"
    }

}