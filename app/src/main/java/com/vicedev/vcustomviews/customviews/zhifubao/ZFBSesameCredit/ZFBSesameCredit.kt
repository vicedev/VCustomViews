package com.vicedev.vcustomviews.customviews.zhifubao.ZFBSesameCredit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.vicedev.vcustomviews.common.DisplayUtils
import com.vicedev.vcustomviews.common.getTextRect

/**
 * Created by vice on 2019/2/8 09:41
 * E-mail:vicedev1001@gmail.com
 *
 * 支付宝芝麻信用
 */

class ZFBSesameCredit : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //当前分值
    var curCores = 0
        set(value) {
            if (value > maxScores || value < 0) return
            field = value
            postInvalidate()
        }

    //最大分值
    var maxScores = 1000

    private val radius by lazy { DisplayUtils.dp2px(context, 60.0f) }
    //芝麻宽
    private val sesameWidth by lazy { DisplayUtils.dp2px(context, 6.0f) }
    //芝麻高
    private val sesameHeight by lazy { DisplayUtils.dp2px(context, 10.0f) }

    //控件宽高
    private val defaultWidth by lazy { 2 * radius + sesameHeight * 2 }
    private val defaultHeight by lazy { radius + sesameHeight * 4 }

    //暗芝麻颜色
    private val darkSesameColor = Color.parseColor("#999999")
    //亮芝麻颜色
    private val lightSesameColor = Color.WHITE

    //外圈芝麻的画笔
    private val sesamePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = darkSesameColor
        }
    }

    //内部文字的画笔
    private val textPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = DisplayUtils.dp2px(context, 30.0f).toFloat()
            isFakeBoldText = true
        }
    }

    //芝麻的路径
    private val sesamePath by lazy {
        Path().apply {
            moveTo(0.0f, radius.toFloat())
            rCubicTo(DisplayUtils.dp2px(context, 2.0f).toFloat(), DisplayUtils.dp2px(context, 0.5f).toFloat(),
                    sesameWidth.toFloat(), DisplayUtils.dp2px(context, 9.5f).toFloat(),
                    0.0f, sesameHeight.toFloat())
            rCubicTo((-sesameWidth).toFloat(), DisplayUtils.dp2px(context, -0.5f).toFloat(),
                    DisplayUtils.dp2px(context, -2.0f).toFloat(), DisplayUtils.dp2px(context, -9.5f).toFloat(),
                    0.0f, (-sesameHeight).toFloat())
            close()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(defaultWidth, defaultHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {

            with(it) {
                for (i in 72..288 step 9) {
                    save()
                    //把坐标轴移动到中间
                    translate(width / 2.0f, height.toFloat() - sesameHeight * 3)
                    rotate(i.toFloat())
                    //画外圈暗芝麻
                    sesamePaint.color = darkSesameColor
                    drawPath(sesamePath, sesamePaint)

                    val lightMax = Math.floor(((curCores * 1.0f / maxScores) * (288 - (72)) + 72).toDouble())
                    if (i <= lightMax && lightMax > 72) {
                        //画外圈亮芝麻
                        sesamePaint.color = lightSesameColor
                        drawPath(sesamePath, sesamePaint)
                    }
                    restore()
                }
            }

            //画中间分值
            textPaint.textSize = DisplayUtils.dp2px(context, 30.0f).toFloat()
            textPaint.isFakeBoldText = true
            val rect = textPaint.getTextRect(curCores.toString())
            it.drawText(curCores.toString(), width / 2.0f - rect.width() / 2.0f, height / 2.0f + rect.height() / 2.0f, textPaint)

            //画底部文字
            textPaint.textSize = DisplayUtils.dp2px(context, 12.0f).toFloat()
            textPaint.isFakeBoldText = false
            val rect2 = textPaint.getTextRect("信用xx")
            it.drawText("信用xx", width / 2.0f - rect2.width() / 2.0f, height.toFloat() - rect2.height() / 2, textPaint)
        }
    }
}