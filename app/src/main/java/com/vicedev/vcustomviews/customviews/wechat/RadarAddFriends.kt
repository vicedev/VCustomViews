package com.vicedev.vcustomviews.customviews.wechat

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.vicedev.vcustomviews.common.DisplayUtils

/**
 * Created by vice on 2019/2/11 21:22
 * E-mail:vicedev1001@gmail.com
 *
 * 微信雷达加朋友
 */


class RadarAddFriends : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //每圈的增加间隔
    private val increaseSpace by lazy { DisplayUtils.dp2px(context, 30.0f).toFloat() }
    //每圈的增加间隔基础
    private val increaseSpaceBase by lazy { DisplayUtils.dp2px(context, 45.0f).toFloat() }
    //最里面圆的半径
    private val innermostCircleRadius by lazy { DisplayUtils.dp2px(context, 40.0f).toFloat() }

    var radarSweepAngle = 0.0f
        set(value) {
            field = value
            invalidate()
        }

    //画圈的画笔
    private val circlePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = DisplayUtils.dp2px(context, 1.0f).toFloat()
            color = Color.WHITE
        }
    }

    var centerX: Float = 0.0f
    var centerY: Float = 0.0f

    override fun onFinishInflate() {
        super.onFinishInflate()
        val anim = ObjectAnimator.ofFloat(this, "radarSweepAngle", 0.0f, 360.0f)
        anim.duration = 5000
        anim.repeatCount = -1
        anim.interpolator = LinearInterpolator()
        anim.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        centerX = width / 2.0f
        centerY = height / 2.0f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            //画背景

            //画雷达
            //画圈
            var maxRadius = 0.0f
            for (i in 0..5) {
                val space: Float = if (i == 0) 0.0f else increaseSpaceBase * i + increaseSpace * i * i / 4
                val radius = innermostCircleRadius + space
                it.drawCircle(centerX, centerY, radius, circlePaint)
                if (i == 4) {
                    maxRadius = radius
                }
            }
            //画扫描
            with(it) {
                save()
                rotate(radarSweepAngle, centerX, centerY)
                drawLine(centerX, centerY, centerX - maxRadius, centerY, circlePaint)
                restore()
            }

            //画中心图
        }
    }
}
