package com.vicedev.vcustomviews.customviews.MFWWriggleTab

import android.content.Context
import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.vicedev.vcustomviews.R
import kotlinx.android.synthetic.main.mfw_wriggle_tab_view.view.*

/**
 * Created by vice on 2019/2/5 10:10
 * E-mail:vicedev1001@gmail.com
 *
 * 马蜂窝蠕动tab
 */

class MFWWriggleTab : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val tabList = listOf("正在旅行", "推荐", "附近", "视频", "春节", "国内", "国外", "网红打卡地", "取景地巡礼",
            "带娃旅行", "海岛游", "海岛游", "情侣出行", "自驾游")

    private var contentList: ArrayList<TextView>? = null
    private var titleList: ArrayList<TextView>? = null
    //当前选中位置
    private var currentPos = 0

    private val vpAdapter by lazy { VPAdapter() }

    override fun onFinishInflate() {
        super.onFinishInflate()

        //ScrollView中的内容
        titleList = ArrayList()
        //ViewPager中的内容
        contentList = ArrayList()
        for (i in 0 until tabList.size) {
            val tabView = LayoutInflater.from(context).inflate(R.layout.mfw_tab_title_item_layout, ll_tab, false) as TextView
            tabView.text = tabList[i]
            val tv = TextView(context)
            tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            tv.gravity = Gravity.CENTER
            tv.setTextColor(Color.RED)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f)
            contentList?.add(tv)
            titleList?.add(tabView)
            ll_tab.addView(tabView)
        }

        with(vp_content) {
            adapter = vpAdapter
            currentItem = 0
        }

        vp_content.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //设置蠕动指示器的百分比变化
                mfw_indicator.setPercentNum(positionOffset)
                //慢慢移动到选中的tab和指示器并居中
                moveTabAndIndicatorToSelectedCenter(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
                currentPos = position
                //修改选中tab的样式
                changeSelectedStyle(position)
            }
        })
        for (i in 0 until titleList!!.size) {
            val tv = titleList!![i]
            tv.setOnClickListener {
                moveTabAndIndicatorToSelectedCenter(i, 0.0f)
                vp_content.setCurrentItem(i, false)
            }
        }

        //初始化状态和位置
        vp_content.currentItem = 0
        //方法第一个tab字体
        changeSelectedStyle(0)
        post {
            //初始化指示器位置
            mfw_indicator.translationX = (titleList!![0].width / 2 - mfw_indicator.width / 2).toFloat()
        }
    }

    /**
     * 修改选中tab的样式
     */
    private fun changeSelectedStyle(position: Int) {
        titleList?.forEach {
            it.scaleX = 1.0f
            it.scaleY = 1.0f
            it.setTextColor(Color.parseColor("#666666"))
        }
        with(titleList!![position]) {
            scaleX = 1.2f
            scaleY = 1.2f
            setTextColor(Color.parseColor("#000000"))
        }
    }

    /**
     * 慢慢移动到选中的tab和指示器并居中
     */
    private fun moveTabAndIndicatorToSelectedCenter(position: Int, positionOffset: Float) {
        val tv = titleList!![position]
        val tvCur = titleList!![currentPos]
        val dex = tv.width / 2 + tvCur.width / 2
        //挪动tab
        sv_tab.scrollTo(((tv.left - ((sv_tab.width - tv.width) / 2) + dex * positionOffset).toInt()), 0)
        //挪动指示器
        mfw_indicator.translationX = tv.x + tv.width / 2 - mfw_indicator.width / 2 + dex * positionOffset
    }

    inner class VPAdapter : PagerAdapter() {
        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            return p0 == p1
        }

        override fun getCount(): Int {
            return tabList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val tv: TextView = contentList!![position]
            tv.text = tabList[position]
            container.addView(tv)
            return tv
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(contentList!![position])
        }
    }

}
