package com.example.myapplication.customview.metroview

/**
 * @Desc:
 * @Author: weiyi1
 * @Date: 2019/9/20.
 * @Email: weiyi1@yy.com
 * @YY: 909019756
 */
class Random {

    fun getRandom(lower: Float, upper: Float): Float {
        val min = Math.min(lower, upper)
        val max = Math.max(lower, upper)
        return getRandom(max - min) + min
    }

    fun getRandom(upper: Float): Float {
        return RANDOM.nextFloat() * upper
    }

    fun getRandom(upper: Int): Int {
        return RANDOM.nextInt(upper)
    }

    companion object {
        private val RANDOM = java.util.Random()
    }

}