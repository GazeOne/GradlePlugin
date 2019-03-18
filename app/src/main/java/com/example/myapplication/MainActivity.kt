package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.retrofitandrxjava.ApiMethods
import com.example.myapplication.retrofitandrxjava.RetrofitUtil


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val rxPractice = RxPractice(this)
//        rxPractice.observable.subscribe(rxPractice.observer)
//        rxPractice.linkUse()
//        rxPractice.justUse()
//        rxPractice.fromArrayUse()
//        rxPractice.fromCallableUse()
//        rxPractice.fromFeatrueUse()
//        rxPractice.fromIterableUse()
//        rxPractice.deferUse()
//        rxPractice.timerUse()
//        //rxPractice.intervalUse()
//        rxPractice.rangeUse()
//        rxPractice.mapUse()
//        rxPractice.flatmapUse()
//        rxPractice.concatmapUse()
//        rxPractice.bufferUse()
//        rxPractice.groupbyUse()
//        rxPractice.scanUse()
//        rxPractice.windowUse()
//        rxPractice.concatUse()
//        rxPractice.concatarrayUse()
//        //rxPractice.mergeUse()
//        rxPractice.zipUse()
//        rxPractice.reduceUse()
//        rxPractice.collectUse()
//        rxPractice.startAndstartwithUse()
//        rxPractice.countUse()
//        rxPractice.delayUse()
//        rxPractice.dooneachUse()
//        rxPractice.doonnextUse()
//        rxPractice.dooncompleteUse()
//        rxPractice.doonsomething()
//        rxPractice.retryUse()
//        rxPractice.filterUse()
//        rxPractice.allUse()
//        rxPractice.skipuntilUse()
//        rxPractice.ambUse()
//        rxPractice.defaultifemptyUse()

//        RetrofitUtil().createRetrofit()

        ApiMethods.getTopMovie(RetrofitUtil().createObserver(), 0, 10)

    }
}
