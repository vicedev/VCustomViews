package com.vicedev.vcustomviews

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vicedev.vcustomviews.common.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_list_item_layout.view.*

class MainActivity : BaseActivity() {

    private var myAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        //隐藏标题栏返回按钮
        hideTitleBarBackBtn()

        myAdapter = MyAdapter()
        with(rv_list) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
            return MyViewHolder(layoutInflater.inflate(R.layout.app_list_item_layout, parent, false))
        }

        override fun onBindViewHolder(vh: MyViewHolder, pos: Int) {
            vh.tvName.text = Constants.APP_MAP.keyAt(pos)
            vh.itemView.setOnClickListener {
                val intent = Intent(this@MainActivity, AppDetailListActivity::class.java)
                intent.putExtra(AppDetailListActivity.APP_NAME, Constants.APP_MAP.keyAt(pos))
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return Constants.APP_MAP.size
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.tv_name
        }
    }


}
