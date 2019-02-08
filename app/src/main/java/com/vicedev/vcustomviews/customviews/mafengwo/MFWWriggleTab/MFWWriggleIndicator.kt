package com.vicedev.vcustomviews.customviews.mfw.MFWWriggleTab

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.vicedev.vcustomviews.common.DisplayUtils
import kotlin.math.PI
import kotlin.math.sin

/**
 * Created by vice on 2019/2/5 10:28
 * E-mail:vicedev1001@gmail.com
 *
 * 马蜂窝蠕动tab的指示器
 */

class MFWWriggleIndicator : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val paint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    //sin曲线的最高点
    private val sinYMax: Float by lazy { DisplayUtils.dp2px(context, 3.0f).toFloat() }
    //sin曲线的路径
    private val sinPath: Path by lazy { Path() }
    //绘制点的个数
    private val pointNum = 20
    //蠕动的百分比0-1
    private var percent = 0.0f
    private val dexY = DisplayUtils.dp2px(context, 1.0f)
    private val dexX = DisplayUtils.dp2px(context, 3.0f)
    //控件高度
    private val viewHeight = DisplayUtils.dp2px(context, sinYMax * 2.0f + dexY * 2)
    private val viewWidth = pointNum * DisplayUtils.dp2px(context, 1.5f) + dexX * 2
    //x轴的上下偏移量，使x轴在中间
    private val xOffset by lazy { viewHeight / 2.0f }

    init {
        with(paint) {
            strokeCap = Paint.Cap.ROUND
            strokeWidth = DisplayUtils.dp2px(context, 2.5f).toFloat()
            color = Color.parseColor("#EFD66D")
            style = Paint.Style.STROKE
        }
    }

    fun setPercentNum(percentNum: Float) {
        percent = percentNum
        postInvalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(viewWidth, viewHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        sinPath.reset()
        for (i in 0..pointNum) {
            if (i == 0) sinPath.moveTo(dexX.toFloat(), calY(i))
            sinPath.lineTo(dexX + (viewWidth - dexX * 2) / 20.0f * i, calY(i))
        }
        canvas.drawPath(sinPath, paint)
    }

    private fun calY(i: Int): Float {
        return (sin(i.toFloat() / pointNum * PI + 2.0f * PI * percent) * sinYMax + xOffset).toFloat()
    }

//    override fun onFinishInflate() {
//        super.onFinishInflate()
//        postDelayed(object : Runnable {
//            override fun run() {
//                percent += 0.1f
//                setPercentNum(percent)
//                postDelayed(this, 50)
//            }
//        }, 50)
//    }
}