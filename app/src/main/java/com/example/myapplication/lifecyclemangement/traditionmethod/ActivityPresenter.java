package com.example.myapplication.lifecyclemangement.traditionmethod;

import android.util.Log;

//在实现类中增加自定义操作（打印日志）
public class ActivityPresenter implements BaseActivityPresenter {

    private static String TAG = ActivityPresenter.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
    }
}
