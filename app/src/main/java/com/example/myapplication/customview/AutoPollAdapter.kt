package com.example.myapplication.customview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.myapplication.R

class AutoPollAdapter(private val mContext: Context, private val mData: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FliperVHoder) {
            val mPluginItem = mData[position % mData.size]
            holder.mPluginName?.text = mPluginItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll, parent, false)
        return FliperVHoder(view)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    inner class FliperVHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPluginName: TextView? = null

        init {
            mPluginName = itemView.findViewById(R.id.tv_content)
        }
    }
}