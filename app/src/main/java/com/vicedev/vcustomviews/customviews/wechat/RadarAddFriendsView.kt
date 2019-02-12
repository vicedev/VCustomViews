package com.vicedev.vcustomviews.customviews.wechat

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.vicedev.vcustomviews.R
import com.vicedev.vcustomviews.common.BitmapUtils
import com.vicedev.vcustomviews.common.DisplayUtils

/**
 * Created by vice on 2019/2/11 21:22
 * E-mail:vicedev1001@gmail.com
 *
 * 微信雷达加朋友
 */


class RadarAddFriendsView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var centerImgId: Int = 0

    //中心图片
    private val centerImgBmp by lazy {
        if (centerImgId == 0) null else BitmapUtils.readBitmapFromResource(resources, centerImgId, innermostCircleRadius.toInt(), innermostCircleRadius.toInt())
    }

    private val centerBmpShader by lazy {
        if (centerImgBmp != null) BitmapShader(centerImgBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP) else null
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RadarAddFriendsView, defStyleAttr, 0)
        centerImgId = ta.getResourceId(R.styleable.RadarAddFriendsView_centerImg, 0)
        ta.recycle()
    }

    //每圈的增加间隔
    private val increaseSpace by lazy { DisplayUtils.dp2px(context, 30.0f).toFloat() }
    //每圈的增加间隔基础
    private val increaseSpaceBase by lazy { DisplayUtils.dp2px(context, 45.0f).toFloat() }
    //最里面圆的半径
    private val innermostCircleRadius by lazy { DisplayUtils.dp2px(context, 40.0f).toFloat() }

    //雷达扫过的角度
    private var radarSweepAngle = 0.0f
        set(value) {
            field = value
            invalidate()
        }

    //画圈的画笔
    private val circlePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = DisplayUtils.dp2px(context, 0.5f).toFloat()
            color = Color.WHITE
        }
    }

    //扫的雷达的画笔
    private val radarPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            shader = SweepGradient(centerX, centerY, Color.TRANSPARENT,
                    Color.parseColor("#66ffffff"))
        }
    }

    //中心图的画笔
    private val centerImgPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = centerBmpShader
        }
    }

    var centerX: Float = 0.0f
    var centerY: Float = 0.0f

    override fun onFinishInflate() {
        super.onFinishInflate()
        val anim = ObjectAnimator.ofFloat(this, "radarSweepAngle", 0.0f, 360.0f)
        anim.duration = 4000
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
                drawArc(RectF(centerX - maxRadius, centerY - maxRadius, centerX + maxRadius, centerY + maxRadius),
                        0.0f, 180.0f, true, radarPaint)
                restore()
            }

            //画中心图
            it.drawCircle(centerX, centerY, innermostCircleRadius, centerImgPaint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        centerImgBmp?.let {
            if (!centerImgBmp!!.isRecycled) {
                centerImgBmp!!.recycle()
            }
        }
    }
}
