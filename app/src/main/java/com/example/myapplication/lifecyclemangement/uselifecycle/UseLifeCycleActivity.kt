package com.example.myapplication.lifecyclemangement.uselifecycle

import android.arch.lifecycle.Lifecycle
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import android.arch.lifecycle.LifecycleRegistry


class UseLifeCycleActivity : AppCompatActivity() {

    private val TAG = UseLifeCycleActivity::class.java.simpleName
    private var mLifecycleRegistry: LifecycleRegistry? = null

    //LifecycleRegistry 类用于注册和反注册需要观察当前组件生命周期的 Observer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_life_cycle)
        mLifecycleRegistry = LifecycleRegistry(this)
        // 注册需要监听的 Observer
        mLifecycleRegistry?.addObserver(ActivityLifeObserver())
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry!!
    }
}
