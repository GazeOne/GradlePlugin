package com.example.myapplication.customview.cardrecyclerview

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log

/**
 * @Desc: itemtouchhelper回调
 * @Author: weiyi1
 * @Date: 2019/8/28.
 * @Email: weiyi1@yy.com
 * @YY: 909019756
 */
class ItemCallback<T> @JvmOverloads constructor(
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    datas: MutableList<T>,
    listener: OnSwipeListener<T>? = null
) : ItemTouchHelper.Callback() {

    private var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    private var mData: MutableList<T>? = null
    private var mListener: OnSwipeListener<T>? = null

    companion object {
        const val TAG = "ItemCallback"
    }

    init {
        this.mAdapter = adapter
        this.mData = datas
        this.mListener = listener
    }

    private fun <T> checkIsNull(t: T?): T {
        if (t == null) {
            throw NullPointerException()
        }
        return t
    }

    fun setOnSwipedListener(mListener: OnSwipeListener<T>) {
        this.mListener = mListener
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = 0
        var swipeFlags = 0
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is CardLayoutManager) {
            swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // 移除 onTouchListener,否则触摸滑动会乱了
        viewHolder.itemView.setOnTouchListener(null)
        val layoutPosition = viewHolder.layoutPosition
        val remove = mData!!.removeAt(layoutPosition)
        mAdapter?.notifyDataSetChanged()
        if (mListener != null) {
            mListener!!.onSwiped(
                viewHolder,
                remove,
                if (direction == ItemTouchHelper.LEFT) CardConfig.SWIPED_LEFT else CardConfig.SWIPED_RIGHT
            )
        }
        // 当没有数据时回调 mListener
        if (mAdapter?.itemCount == 0) {
            if (mListener != null) {
                mListener!!.onSwipedClear()
            }
        }
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            var ratio = dX / getThreshold(recyclerView, viewHolder)
            Log.i(TAG, "ratio = $ratio, dx = $dX, threshold = ${getThreshold(recyclerView, viewHolder)}")
            // ratio 最大为 1 或 -1
            if (ratio > 1) {
                ratio = 1f
            } else if (ratio < -1) {
                ratio = -1f
            }
            itemView.rotation = ratio * CardConfig.DEFAULT_ROTATE_DEGREE
            val childCount = recyclerView.childCount
            // 当数据源个数大于最大显示数时
            if (childCount > CardConfig.DEFAULT_SHOW_ITEM) {
                for (position in 1 until childCount - 1) {
                    val index = childCount - position - 1
                    Log.i(TAG, "index = $index")
                    val view = recyclerView.getChildAt(position)
                    view.scaleX = 1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE
                    view.scaleY = 1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE
                    view.translationY =
                        (index - Math.abs(ratio)) * itemView.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y
                }
            } else {
                // 当数据源个数小于或等于最大显示数时
                for (position in 0 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX = 1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE
                    view.scaleY = 1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE
                    view.translationY =
                        (index - Math.abs(ratio)) * itemView.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y
                }
            }
            if (mListener != null) {
                if (ratio != 0f) {
                    mListener!!.onSwiping(
                        viewHolder,
                        ratio,
                        if (ratio < 0) CardConfig.SWIPING_LEFT else CardConfig.SWIPING_RIGHT
                    )
                } else {
                    mListener!!.onSwiping(viewHolder, ratio, CardConfig.SWIPING_NONE)
                }
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.rotation = 0f
    }

    private fun getThreshold(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Float {
        return recyclerView.width * getSwipeThreshold(viewHolder)
    }
}