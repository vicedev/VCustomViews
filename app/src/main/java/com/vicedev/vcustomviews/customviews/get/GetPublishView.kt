package com.vicedev.vcustomviews.customviews.get

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.RelativeLayout
import com.vicedev.vcustomviews.common.DisplayUtils
import kotlinx.android.synthetic.main.get_publish_view.view.*
import kotlin.math.sqrt

/**
 * Created by vice on 2019/2/4 09:50
 * E-mail:vicedev1001@gmail.com
 *
 * get 发布内容弹出效果
 */

class GetPublishView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //是否已经弹出
    private var isPoped = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        //点击弹出
        publish.setOnClickListener {
            if (!isPoped) open() else close()
            isPoped = !isPoped
        }
    }

    private fun open() {
        reset()

        val animDuration = 300.toLong()
        publish.animate().rotation(-45f).setDuration(animDuration).start()
        val dex = DisplayUtils.dp2px(context, 80.0f * sqrt(3.0f) / 2.0f).toFloat()
        val dex2 = DisplayUtils.dp2px(context, 80.0f * 0.5f).toFloat()
        item1.animate().translationY((-DisplayUtils.dp2px(context, 80f)).toFloat()).setDuration(animDuration).setInterpolator(BounceInterpolator()).start()
        item2.animate().translationX(-dex).translationY(-dex2).setDuration(animDuration).setInterpolator(BounceInterpolator()).setStartDelay(50).start()
        item3.animate().translationX(-dex).translationY(dex2).setDuration(animDuration).setInterpolator(BounceInterpolator()).setStartDelay(100).start()
        item4.animate().translationY((-DisplayUtils.dp2px(context, -80f)).toFloat()).setDuration(animDuration).setInterpolator(BounceInterpolator()).setStartDelay(150).start()
    }

    private fun close() {
        publish.animate().rotation(0f).setDuration(300).start()
        closeAnim(item1)
        closeAnim(item2)
        closeAnim(item3)
        closeAnim(item4)
    }

    private fun reset() {
        item1.rotation = 0.0f
        item2.rotation = 0.0f
        item3.rotation = 0.0f
        item4.rotation = 0.0f
    }

    private fun closeAnim(view: View) {
        view.animate().rotation(-360.0f * 5).setDuration(200).setInterpolator(null).setStartDelay(0).start()
        view.animate().translationX(0f).translationY(0f).setInterpolator(null).setStartDelay(150).start()
    }

}