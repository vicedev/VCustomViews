package com.vicedev.vcustomviews

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vicedev.vcustomviews.common.Constants
import kotlinx.android.synthetic.main.activity_app_detail_list.*
import kotlinx.android.synthetic.main.app_detail_list_item_layout.view.*

class AppDetailListActivity : AppCompatActivity() {

    companion object {
        const val APP_NAME = "app_name"
    }

    private var myAdapter: MyAdapter? = null
    private var appName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_detail_list)

        init()
    }

    private fun init() {
        appName = intent.getStringExtra(APP_NAME)

        supportActionBar?.title = appName

        myAdapter = MyAdapter(Constants.APP_MAP[appName]!!)

        with(rv_app_list) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@AppDetailListActivity)
        }
    }

    inner class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private var customViewNameList: ArrayList<String>? = null

        constructor(customViewNameList: ArrayList<String>) : this() {
            this.customViewNameList = customViewNameList
        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
            return MyViewHolder(layoutInflater.inflate(R.layout.app_detail_list_item_layout, parent, false))
        }

        override fun onBindViewHolder(vh: MyViewHolder, pos: Int) {
            vh.tvName.text = customViewNameList?.get(pos)
            vh.itemView.setOnClickListener {
                val intent = Intent(this@AppDetailListActivity, DetailActivity::class.java)
                intent.putExtra(AppDetailListActivity.APP_NAME, appName)
                intent.putExtra(DetailActivity.CUSTOM_VIEW_NAME, Constants.APP_MAP[appName]?.get(pos))
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return customViewNameList?.size ?: 0
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.tv_name
        }
    }
}
