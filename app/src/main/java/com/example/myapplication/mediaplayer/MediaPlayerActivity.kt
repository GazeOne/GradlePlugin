package com.example.myapplication.mediaplayer

import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_media_player.*

class MediaPlayerActivity : AppCompatActivity() {

    private var receiver: MediaStateBroadcast? = null

    private var myService: IServiceInterface? = null

    private val mConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.i(TAG, "onServiceDisconnected")
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            myService = p1 as IServiceInterface
            Log.i(TAG, "onServiceConnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)

        play?.setOnClickListener {
            myService?.play()
        }

        next?.setOnClickListener {
            myService?.next()
        }

        stop?.setOnClickListener {
            myService?.stop()
        }

        receiver = MediaStateBroadcast()
        val filter = IntentFilter()
        filter.addAction("MediaPlayerActivity")
        registerReceiver(receiver, filter)

        val intent = Intent(this, MediaPlayService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    inner class MediaStateBroadcast : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

        }

    }

    companion object {
        const val TAG = "MediaPlayerActivity"
    }
}
