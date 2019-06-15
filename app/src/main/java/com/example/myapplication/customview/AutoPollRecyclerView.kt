package com.example.myapplication.customview

import android.content.Context
import android.support.annotation.Nullable
import android.view.MotionEvent
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import java.lang.ref.WeakReference

class AutoPollRecyclerView(context: Context, @Nullable attrs: AttributeSet) : RecyclerView(context, attrs) {
    internal var autoPollTask: AutoPollTask
    private var running: Boolean = false //标示是否正在自动滚动
    private var canRun: Boolean = false//标示是否可以自动滚动,可在不需要的是否置false

    init {
        autoPollTask = AutoPollTask(this)
    }

    internal class AutoPollTask//使用弱引用持有外部类引用->防止内存泄漏
        (reference: AutoPollRecyclerView) : Runnable {
        private val mReference: WeakReference<AutoPollRecyclerView> = WeakReference<AutoPollRecyclerView>(reference)

        override fun run() {
            val recyclerView = mReference.get()
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                recyclerView.scrollBy(2, 2)
                recyclerView.postDelayed(recyclerView.autoPollTask, TIME_AUTO_POLL)
            }
        }
    }

    //开启:如果正在运行,先停止->再开启
    fun start() {
        if (running)
            stop()
        canRun = true
        running = true
        postDelayed(autoPollTask, TIME_AUTO_POLL)
    }

    fun stop() {
        running = false
        removeCallbacks(autoPollTask)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> if (running)
                stop()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> if (canRun)
                start()
        }
        return super.onTouchEvent(e)
    }

    companion object {
        private const val TIME_AUTO_POLL: Long = 16
    }
}