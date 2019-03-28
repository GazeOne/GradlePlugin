package com.example.myapplication.viewandeventdispatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {

    private static String TAG = "CustomTV";

    public CustomButton(Context context) {
        this(context, null, 0);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent:==" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent:== ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent:== ACTION_MOVE");
                return true;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent:== ACTION_UP");
                return true;
        }
        return false;
    }

    //dispatchTouchEvent()返回true，后续事件（ACTION_MOVE、ACTION_UP）会再传递，
    // 如果返回false，dispatchTouchEvent()就接收不到ACTION_UP、ACTION_MOVE。
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent:==" + super.dispatchTouchEvent(event));
        return true;
    }
}
