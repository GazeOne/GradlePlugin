package com.example.myapplication.retrofitandrxjava

import android.util.Log
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitUtil {

    companion object {
        const val TAG = "RetrofitUtil"
    }

    fun createRetrofit() {
        val baseUrl = "https://api.douban.com/v2/movie/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型
            .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
            .build()
        retrofit.create(ApiService::class.java).getTopMovie(0, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Movie> {
                override fun onComplete() {
                    Log.i(TAG, "onComplete: ")
                }

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: Movie) {
                    Log.i(TAG, "onNext: " + t.title)
                    val list = t.subjects
                    for (sub in list) {
                        Log.i(TAG, "onNext: " + sub.id + "," + sub.year + "," + sub.title)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i(TAG, "onError: " + e.message)
                }

            })
    }

    fun createObserver(): Observer<Movie> {

        return object : Observer<Movie> {
            override fun onComplete() {
                Log.d(TAG, "onComplete: "); }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: "); }

            override fun onNext(t: Movie) {
                Log.i(TAG, "onNext: " + t.title)
                val list = t.subjects
                for (sub in list) {
                    Log.i(TAG, "onNext: " + sub.id + "," + sub.year + "," + sub.title)
                }
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, "onError: "); }

        }

    }
}