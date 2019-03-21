package com.example.myapplication.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


class RetrofitPractice<T> {

    fun createRetrofit(uri: String): Retrofit {
        //获取实例
        return Retrofit.Builder()
            //设置OKHttpClient,如果不设置会提供一个默认的
            .client(OkHttpClient())
            //设置baseUrl
            .baseUrl(uri)
            //添加Gson转换器
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createGithubService(retrofit: Retrofit, clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}