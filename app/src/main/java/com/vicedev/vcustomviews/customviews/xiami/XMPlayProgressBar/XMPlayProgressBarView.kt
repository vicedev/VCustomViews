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

    //最小变换label方向的距离
    private val minMoveDirection by lazy { DisplayUtils.dp2px(context, 1.0f) }

    private var lastX = 0.0f
    private var touchX = 0.0f

    //总时间
    private var total = 360
        set(value) {
            if (value <= 0) return
            field = value
        }

    //当前时间
    private var current = 0
        set(value) {
            field = if (value > total) total else value
        }

    init {
        orientation = VERTICAL
        setWillNotDraw(false)

        xmPlayDragView.text = currentLabel()

        //添加上方显示的LabelView
        addView(xmPlayLabelView)
        //添加拖拽的view，隔一点距离
        val lp = MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
        lp.topMargin = DisplayUtils.dp2px(context, 10.0f)
        addView(xmPlayDragView, lp)

        xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_HIDE).text = currentLabel()

        xmPlayDragView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    touchX = event.x
                    xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_CENTER)
                }

                MotionEvent.ACTION_MOVE -> {
                    val dex = event.x - lastX

                    //根据拖拽移动控件
                    moveView(event)

                    if (Math.abs(dex) > minMoveDirection) {
                        xmPlayLabelView.setDisplayMode(
                                when {
                                    dex < 0 -> XMPlayLabelView.MODE_RIGHT
                                    dex > 0 -> XMPlayLabelView.MODE_LEFT
                                    else -> XMPlayLabelView.MODE_CENTER
                                }
                        )
                        lastX = event.x
                    }
                }

                MotionEvent.ACTION_UP -> {
                    xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_HIDE)
                }
            }
            true
        }
    }

    //根据拖拽移动控件
    private fun moveView(event: MotionEvent) {
        //拖拽的view
        var dragTargetX = xmPlayDragView.x + event.x - touchX
        dragTargetX = when {
            dragTargetX < 0 -> 0.0f
            dragTargetX > width - xmPlayDragView.width -> (width - xmPlayDragView.width).toFloat()
            else -> dragTargetX
        }
        xmPlayDragView.x = dragTargetX

        //label view
        var labelTargetX = dragTargetX - ((xmPlayLabelView.width - xmPlayDragView.width) / 2)
        labelTargetX = when {
            labelTargetX < 0 -> 0.0f
            labelTargetX > width - xmPlayLabelView.width -> (width - xmPlayLabelView.width).toFloat()
            else -> labelTargetX
        }
        xmPlayLabelView.x = labelTargetX

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