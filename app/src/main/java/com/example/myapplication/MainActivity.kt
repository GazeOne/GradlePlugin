package com.example.myapplication

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.lifecyclemangement.traditionmethod.TraditionLifeManageActivity
import com.example.myapplication.lifecyclemangement.uselifecycle.UseLifeCycleActivity
import com.example.myapplication.livedataandviewmodel.UserViewModel
import com.example.myapplication.retrofit.GitHubService
import com.example.myapplication.retrofit.RetrofitPractice
import com.example.myapplication.retrofitandrxjava.ApiMethods
import com.example.myapplication.retrofitandrxjava.RetrofitUtil
import com.example.myapplication.rxjava.RxJavaPractice
import com.example.myapplication.retrofit.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.myapplication.livedataandviewmodel.User
import android.arch.lifecycle.ViewModelProviders
import com.example.myapplication.Javareflect.JavaReflectMainActivity
import com.example.myapplication.hookams.HookAmsActivity
import com.example.myapplication.hookstartactivity.HookActivity
import com.example.myapplication.musicdemo.MusicAvtivity
import com.example.myapplication.musicdemo2.MusicActivity2
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mTraButton: Button? = null
    private var mLifeButton: Button? = null
    private var mUserTv: TextView? = null
    private var mUserChange: Button? = null
    private var mUserViewModel: UserViewModel? = null
    private var mGoMusicButton1: Button? = null
    private var mGoMusicButton2: Button? = null
    private var mGoJavaReflectButton: Button? = null
    private var mGoHookAms: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTraButton = findViewById(R.id.go_tarditionlifemanage)
        mLifeButton = findViewById(R.id.go_uselifecycle)
        mUserTv = findViewById(R.id.user)
        mUserChange = findViewById(R.id.change_user)
        mGoMusicButton1 = findViewById(R.id.go_music)
        mGoMusicButton2 = findViewById(R.id.go_music2)
        mGoJavaReflectButton = findViewById(R.id.go_javareflect)
        mGoHookAms = findViewById(R.id.go_hookams)
        val rxPractice = RxJavaPractice(this)
        rxPractice.observable.subscribe(rxPractice.observer)
        rxPractice.linkUse()
        rxPractice.justUse()
        rxPractice.fromArrayUse()
        rxPractice.fromCallableUse()
        rxPractice.fromFeatrueUse()
        rxPractice.fromIterableUse()
        rxPractice.deferUse()
        rxPractice.timerUse()
        //rxPractice.intervalUse()
        rxPractice.rangeUse()
        rxPractice.mapUse()
        rxPractice.flatmapUse()
        rxPractice.concatmapUse()
        rxPractice.bufferUse()
        rxPractice.groupbyUse()
        rxPractice.scanUse()
        rxPractice.windowUse()
        rxPractice.concatUse()
        rxPractice.concatarrayUse()
        //rxPractice.mergeUse()
        rxPractice.zipUse()
        rxPractice.reduceUse()
        rxPractice.collectUse()
        rxPractice.startAndstartwithUse()
        rxPractice.countUse()
        rxPractice.delayUse()
        rxPractice.dooneachUse()
        rxPractice.doonnextUse()
        rxPractice.dooncompleteUse()
        rxPractice.doonsomething()
        rxPractice.retryUse()
        rxPractice.filterUse()
        rxPractice.allUse()
        rxPractice.skipuntilUse()
        rxPractice.ambUse()
        rxPractice.defaultifemptyUse()

        RetrofitUtil().createRetrofit()

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
                Log.i(TAG, repos?.get(0).toString())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {

            }
        })

        // 取消
        call.cancel()
        mTraButton?.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, TraditionLifeManageActivity::class.java)
            startActivity(intent)
        }

        mLifeButton?.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, UseLifeCycleActivity::class.java)
            startActivity(intent)
        }

        //  view model.observe
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        mUserViewModel?.data?.observe(this,
            Observer<User> { user -> if (user != null) mUserTv?.text = user.toString() })

        //	改变 User 内容
        mUserChange?.setOnClickListener {
            if (mUserViewModel != null && mUserViewModel?.data != null) {
                mUserViewModel?.changeData()
            }
        }

        mGoMusicButton1?.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MusicAvtivity::class.java)
            startActivity(intent)
        }

        mGoMusicButton2?.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MusicActivity2::class.java)
            startActivity(intent)
        }

        mGoJavaReflectButton?.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, JavaReflectMainActivity::class.java)
            startActivity(intent)
        }

        mGoHookAms?.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, HookAmsActivity::class.java)
            startActivity(intent)
        }

        go_hookstartactivity.setOnClickListener{
            val intent = Intent()
            intent.setClass(this, HookActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        val TAG = "MainActivity"
    }

}
