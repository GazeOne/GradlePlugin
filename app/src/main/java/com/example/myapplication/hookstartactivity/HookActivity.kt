package com.example.myapplication.hookstartactivity

import android.app.Activity
import android.app.Instrumentation
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.hookams.RefInvoke
import kotlinx.android.synthetic.main.activity_hook.*
import com.example.myapplication.MainActivity
import android.content.Intent




class HookActivity : AppCompatActivity() {

    private var mInstrumentation: Instrumentation? = null
    private var mEvilInstrumentation: EvilInstrumentation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hook)
        mInstrumentation =
            RefInvoke.getFieldObject(Activity::class.java, this@HookActivity, "mInstrumentation") as Instrumentation?
        mEvilInstrumentation = EvilInstrumentation(mInstrumentation)

        RefInvoke.setFieldObject(Activity::class.java, this@HookActivity, "mInstrumentation", mEvilInstrumentation)
        ishook_test.setOnClickListener{
            val intent = Intent(this@HookActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
