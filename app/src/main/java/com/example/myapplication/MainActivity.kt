package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.retrofit.GitHubService
import com.example.myapplication.retrofit.RetrofitPractice
import com.example.myapplication.retrofitandrxjava.ApiMethods
import com.example.myapplication.retrofitandrxjava.RetrofitUtil
import com.example.myapplication.rxjava.RxJavaPractice
import com.example.myapplication.retrofit.Repo
import java.io.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val rxPractice = RxJavaPractice(this)
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

        val retrofit = RetrofitPractice<GitHubService>()
            .createRetrofit("https://api.github.com/")
        val gitHubService = RetrofitPractice<GitHubService>().createGithubService(retrofit, GitHubService::class.java)


        //https://api.github.com/users/octocat/repos
        val call = gitHubService.listRepos("octocat")
        //call只能调用一次。否则会抛 IllegalStateException
        val clone = call.clone()

        //异步请求
        clone.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                // Get result bean from response.body()
                val repos = response.body()
                // Get header item from response
                val links = response.headers().get("Link")
                /**
                 * 不同于retrofit1 可以同时操作序列化数据javabean和header
                 */
                Log.i(TAG, repos!![0].toString())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {

            }
        })

        // 取消
        call.cancel()
    }

    companion object {
        val TAG = "MainActivity"
    }
}
