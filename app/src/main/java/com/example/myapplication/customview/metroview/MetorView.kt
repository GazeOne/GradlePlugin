package com.example.myapplication.customview.metroview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R
import javax.annotation.Nullable


/**
 * @Desc: 流星雨
 * @Author: weiyi1
 * @Date: 2019/9/20.
 * @Email: weiyi1@yy.com
 * @YY: 909019756
 */
class MetorView @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val NUMOFMETOR = 1
    private val DELAY = 5

    private var metros = mutableListOf<Metor>()
    private var bitmap: Bitmap

    init {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.link_match_fly_light)
    }


    private fun resize(width: Int, height: Int) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        for (i in 0 until NUMOFMETOR) {
            metros.add(Metor.create(width, height, bitmap, paint))
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w != oldw || h != oldh) {
            resize(w, h)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (metor in metros) {
            metor.draw(canvas)
        }
        handler.postDelayed(runnable, DELAY.toLong())
    }

    private val runnable = Runnable { invalidate() }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    fun stop() {
        handler?.removeCallbacks(runnable)
    }
}