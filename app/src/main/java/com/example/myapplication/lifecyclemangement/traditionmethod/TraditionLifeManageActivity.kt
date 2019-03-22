package com.example.myapplication.lifecyclemangement.traditionmethod

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class TraditionLifeManageActivity : AppCompatActivity() {

    private var mBasePresenter: ActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tradition_lifecycle_management)
        mBasePresenter = ActivityPresenter()
    }

    override fun onStart() {
        super.onStart()
        mBasePresenter?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBasePresenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBasePresenter?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mBasePresenter?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBasePresenter?.onDestroy()
    }

}
