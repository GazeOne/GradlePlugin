package com.example.myapplication.lifecyclemangement.traditionmethod;

//比如我们需要监听某个 Activity 生命周期的变化，在生命周期改变的时候打印日志，
// 一般做法构造回调的方式，先定义基础 BaseActivityPresenter 接口
public interface BaseActivityPresenter extends BasePresenter{

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
