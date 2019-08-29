package com.example.myapplication.customview.cardrecyclerview

import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * @Desc:卡片布局管理器
 * @Author: weiyi1
 * @Date: 2019/8/28.
 * @Email: weiyi1@yy.com
 * @YY: 909019756
 */
class CardLayoutManager constructor(recyclerView: RecyclerView, touchHelper: ItemTouchHelper) :
    RecyclerView.LayoutManager() {

    private var mRecyclerView: RecyclerView
    private var mItemTouchHelper: ItemTouchHelper

    companion object {
        const val TAG = "CardLayoutManager"
    }

    init {
        this.mRecyclerView = recyclerView
        this.mItemTouchHelper = touchHelper
    }

    private fun <T> checkIsNull(t: T?): T {
        if (t == null) {
            throw NullPointerException()
        }
        return t
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler!!)
        val itemCount = itemCount
        // 当数据源个数大于最大显示数时
        if (itemCount > CardConfig.DEFAULT_SHOW_ITEM) {
            for (position in CardConfig.DEFAULT_SHOW_ITEM downTo 0) {
                val view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)
                // recyclerview 布局
                layoutDecoratedWithMargins(
                    view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view)
                )
                Log.i(
                    TAG,
                    "width = $width, height = $height," +
                            " wspace = $widthSpace, hspace = $heightSpace, " +
                            "dwidth = ${getDecoratedMeasuredWidth(view)}, " +
                            "dheight = ${getBottomDecorationHeight(view)}"
                )

                if (position == CardConfig.DEFAULT_SHOW_ITEM) {
                    view.scaleX = 1 - (position - 1) * CardConfig.DEFAULT_SCALE
                    view.scaleY = 1 - (position - 1) * CardConfig.DEFAULT_SCALE
                    view.translationY = (position - 1) * view.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y
                } else if (position > 0) {
                    view.scaleX = 1 - position * CardConfig.DEFAULT_SCALE
                    view.scaleY = 1 - position * CardConfig.DEFAULT_SCALE
                    view.translationY = position * view.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时
            for (position in itemCount - 1 downTo 0) {
                val view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)
                // recyclerview 布局
                layoutDecoratedWithMargins(
                    view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view)
                )

                if (position > 0) {
                    view.scaleX = 1 - position * CardConfig.DEFAULT_SCALE
                    view.scaleY = 1 - position * CardConfig.DEFAULT_SCALE
                    view.translationY = position * view.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
            }
        }
    }

    private val mOnTouchListener = View.OnTouchListener { v, event ->
        val childViewHolder = mRecyclerView.getChildViewHolder(v)
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            mItemTouchHelper.startSwipe(childViewHolder)
        }
        false
    }
}