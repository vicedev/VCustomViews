package com.vicedev.vcustomviews.customviews.xiami.XMPlayProgressBar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.vicedev.vcustomviews.R
import com.vicedev.vcustomviews.common.DisplayUtils

/**
 * Created by vice on 2019/2/28 23:32
 * E-mail:vicedev1001@gmail.com
 * 虾米音乐的音乐进度条
 */

class XMPlayProgressBarView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val xmPlayDragView: TextView by lazy { inflate(context, R.layout.xiami_drag_view, null) as TextView }
    private val xmPlayLabelView: XMPlayLabelView by lazy { inflate(context, R.layout.xiami_label_view, null) as XMPlayLabelView }

    private var dragMaxX = 0.0f
    private val labelMaxX: Float
        get() {
            return (width - xmPlayLabelView.getCurrentWidth()).toFloat()
        }

    private val progressY by lazy { xmPlayDragView.y + xmPlayDragView.height / 2.0f }

    private val progressPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = DisplayUtils.dp2px(context, 3f).toFloat()
        }
    }

    private var lastX = 0.0f
    private var touchX = 0.0f

    //总时间
    private var total = 1

    //当前时间
    private var current = 0

    init {
        orientation = VERTICAL
        setWillNotDraw(false)

        //添加上方显示的LabelView
        addView(xmPlayLabelView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_HIDE).text = currentLabel()

        //添加拖拽的view，隔一点距离
        val lp = MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
        lp.topMargin = DisplayUtils.dp2px(context, 10.0f)
        addView(xmPlayDragView, lp)
        xmPlayDragView.text = currentLabel()

        xmPlayDragView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    touchX = event.x
                    xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_CENTER)
                    moveLabelView()
                }

                MotionEvent.ACTION_MOVE -> {
                    val dex = event.x - lastX

                    //根据拖拽移动控件
                    moveView(event)
                    //计算当前进度
                    val flag = calCurrent()
                    //设置文字
                    setLabelAndDragText()

                    xmPlayLabelView.setDisplayMode(
                            when {
                                flag < 0 -> XMPlayLabelView.MODE_LEFT
                                flag > 0 -> XMPlayLabelView.MODE_RIGHT
                                else -> xmPlayLabelView.currentMode
                            }
                    )
                    lastX = event.x

                    invalidate()
                }

                MotionEvent.ACTION_UP -> {
                    xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_HIDE)
                }
            }
            true
        }
    }

    /**
     * 根据当前的进度调整显示
     */
    private fun toCurrent() {
        xmPlayDragView.text = currentLabel()
        xmPlayLabelView.setDisplayMode(XMPlayLabelView.MODE_HIDE).text = currentLabel()
        val dragX = current.toFloat() / total * dragMaxX
        var targetLabelX = dragX - ((xmPlayLabelView.getCurrentWidth() - xmPlayDragView.width) / 2)
        targetLabelX = when {
            targetLabelX < 0 -> 0.0f
            targetLabelX > labelMaxX -> labelMaxX
            else -> targetLabelX
        }
        xmPlayDragView.x = dragX
        xmPlayLabelView.x = targetLabelX
        postInvalidate()
    }

    //根据拖拽移动控件
    private fun moveView(event: MotionEvent) {
        //拖拽的view
        moveDragView(event)
        //label view
        moveLabelView()
    }

    /**
     * 计算当前的进度
     * @return 1：数值增加，-1：数值减少，0：数值不变
     */
    private fun calCurrent(): Int {
        val current = (xmPlayDragView.x / dragMaxX * total).toInt()
        val flag = current - this.current
        this.current = current
        return flag
    }

    private fun moveDragView(event: MotionEvent) {
        var dragTargetX = xmPlayDragView.x + event.x - touchX
        dragTargetX = when {
            dragTargetX < 0 -> 0.0f
            dragTargetX > dragMaxX -> dragMaxX
            else -> dragTargetX
        }
        xmPlayDragView.x = dragTargetX
    }

    private fun moveLabelView() {
        var labelTargetX = xmPlayDragView.x - ((xmPlayLabelView.getCurrentWidth() - xmPlayDragView.width) / 2)
        labelTargetX = when {
            labelTargetX < 0 -> 0.0f
            labelTargetX > labelMaxX -> labelMaxX
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

    /**
     * 设置label和拖拽view的文字
     */
    private fun setLabelAndDragText() {
        xmPlayLabelView.text = currentLabel()
        xmPlayDragView.text = currentLabel()
    }

    /**
     * 设置总进度值
     */
    fun setTotal(total: Int) {
        if (total <= 0) return
        this.total = total
        toCurrent()
    }

    /**
     * 设置当前进度值
     */
    fun setCurrent(current: Int) {
        this.current = if (current > total) total else current
        toCurrent()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        dragMaxX = (width - xmPlayDragView.width).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //画渐变的进度条
        progressPaint.shader = LinearGradient(0f, 0f, xmPlayDragView.x, 0f, Color.TRANSPARENT, Color.parseColor("#ff8800"), Shader.TileMode.CLAMP)
        canvas?.run {
            drawLine(0f, progressY, xmPlayDragView.x, progressY, progressPaint)
        }
    }

}