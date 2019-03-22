package com.example.myapplication.lifecyclemangement.uselifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;
import com.example.myapplication.lifecyclemangement.traditionmethod.BaseActivityPresenter;

public class ActivityLifeObserver implements BaseActivityPresenter, LifecycleObserver {

    private String TAG = ActivityLifeObserver.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Override
    public void onStart() {
        Log.i(TAG, "onStart()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPause() {
        Log.i(TAG, "onPause()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Override
    public void onStop() {
        Log.i(TAG, "onStop()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
    }
}
