package com.vicedev.vcustomviews.customviews.zhifubao.ZFBSesameCredit

import android.animation.ObjectAnimator
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import com.vicedev.vcustomviews.common.showToast
import kotlinx.android.synthetic.main.zfb_sesame_credit_container.view.*

/**
 * Created by vice on 2019/2/8 13:44
 * E-mail:vicedev1001@gmail.com
 *
 * 支付宝芝麻信用的容器
 */

class ZFBSesameCreditContainer : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onFinishInflate() {
        super.onFinishInflate()
        btn_set_score_anim.setOnClickListener {
            set(true)
        }

        btn_set_score_no_anim.setOnClickListener {
            set(false)
        }

        zfb_sesame_credit.maxScores = 1000
        zfb_sesame_credit.curCores = 800
    }

    private fun set(anim: Boolean = false) {
        clearAnimation()
        try {
            val maxScoreText = et_max_score.text.toString()
            if (TextUtils.isEmpty(maxScoreText)) {
                zfb_sesame_credit.maxScores = 1000
            } else {
                zfb_sesame_credit.maxScores = maxScoreText.toInt()
            }
        } catch (e: Exception) {
            context.showToast("最大分输入有问题，已设置为默认值1000")
            zfb_sesame_credit.maxScores = 1000
        }
        try {
            val curScore = et_score.text.toString()
            val curScoreNum: Int
            curScoreNum = if (TextUtils.isEmpty(curScore)) {
                800
            } else {
                curScore.toInt()
            }
            if (anim) {
                val objectAnimator = ObjectAnimator.ofInt(zfb_sesame_credit, "curCores", 0, curScoreNum)
                        .setDuration(800)
                objectAnimator.interpolator = AccelerateDecelerateInterpolator()
                objectAnimator.start()

            } else {
                zfb_sesame_credit.curCores = curScoreNum
            }
        } catch (e: Exception) {
            context.showToast("当前分输入有问题，已设置为默认值800")
        }
    }
}