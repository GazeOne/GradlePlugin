package com.example.myapplication.customview.metroview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.Log


/**
 * @Desc: 流星
 * @Author: weiyi1
 * @Date: 2019/9/20.
 * @Email: weiyi1@yy.com
 * @YY: 909019756
 */
class Metor {

    private val random: Random
    private val position: Point //左上
    private var angle: Float = 0.toFloat()
    private val xIncrement: Float
    private val yIncrement: Float
    private val paint: Paint
    private var bitmap: Bitmap
    private var bitmapWidth: Int
    private var bitmaptHeight: Int

    companion object {

        private val XINCREMENT_LOWER = 10f
        private val XINCREMENT_UPPER = 20f

        private val YINCREMENT_LOWER = 100f
        private val YINCREMENT_UPPER = 200f
        private val angle = 135f
        const val TAG = "Metor"

        fun create(width: Int, height: Int, bitmap: Bitmap, paint: Paint): Metor {
            val random = Random()
            val x = random.getRandom(width / 2f, width.toFloat())
            val y = random.getRandom(height / 2f, height.toFloat()) - bitmap.height
            val position = Point(x.toInt(), y.toInt())
            val xIncrement = random.getRandom(XINCREMENT_LOWER, XINCREMENT_UPPER)
            val yIncrement = random.getRandom(YINCREMENT_LOWER, YINCREMENT_UPPER)
            return Metor(random, position, angle, xIncrement, yIncrement, bitmap, paint)
        }
    }

    constructor(
        random: Random,
        position: Point,
        angle: Float,
        xIncrement: Float,
        yIncrement: Float,
        bitmap: Bitmap,
        paint: Paint
    ) {
        this.random = random
        this.position = position
        this.angle = angle
        this.xIncrement = xIncrement
        this.yIncrement = yIncrement
        this.bitmap = bitmap
        this.paint = paint
        this.bitmapWidth = bitmap.width
        this.bitmaptHeight = bitmap.height
    }

    private fun move(width: Int, height: Int) {
        val x = position.x + xIncrement * Math.cos(angle.toDouble())
        val y = position.y + yIncrement * Math.sin(angle.toDouble())
        Log.i(
            TAG,
            "move : x = ${xIncrement * Math.cos(angle.toDouble())}, y = ${yIncrement * Math.sin(angle.toDouble())}"
        )
        position.set(x.toInt(), y.toInt())

        if (!isInside(width, height)) {
            reset(width, height)
        }
    }

    private fun isInside(width: Int, height: Int): Boolean {
        val x = position.x
        val y = position.y

        //检测左下和右上两个点是否在屏幕中，左下
        val xLB = position.x
        val yLB = position.y + bitmaptHeight
        //右上
        val xRU = position.x + bitmapWidth
        val yRU = position.y

        Log.i(TAG, "width = $width, height = $height, xLB = $xLB, yLB =$yLB, xRU = $xRU, yRU = $yRU ")


        if ((xLB in 0..width && yLB in 0..height)
            || (xRU in 0..width && yRU in 0..height)
        ) {
            return true
        }

        return false
    }

    private fun reset(width: Int, height: Int) {
        position.x = random.getRandom(width)
        position.y = random.getRandom(height) - bitmaptHeight
    }

    fun draw(canvas: Canvas) {
        val width = canvas.width
        val height = canvas.height
        move(width, height)
        canvas.drawBitmap(bitmap, position.x.toFloat(), position.y.toFloat(), paint)
    }
}