package com.example.myapplication.musicdemo2

import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.musicdemo.data.MyMusics

class MusicActivity2 : AppCompatActivity() {

    private var tvTitle: TextView? = null
    private var tvAuthor: TextView? = null
    private var btnPlay: ImageButton? = null
    private var btnStop: ImageButton? = null

    private var receiver1: Receiver1? = null

    private var myService: IServiceInterface? = null

    companion object {
        const val TAG = "MusicActivity2"
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.i(TAG, "onServiceDisconnected")
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            myService = p1 as IServiceInterface
            Log.i(TAG, "onServiceConnected")
        }
    }

    //0x11 0x12 0x13 stop play pause
    private var status: Int = 0x11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_avtivity)

        tvTitle = findViewById(R.id.tvTitle)
        tvAuthor = findViewById(R.id.tvAuthor)
        btnPlay = findViewById(R.id.btnPlay)
        btnStop = findViewById(R.id.btnStop)

        btnPlay?.setOnClickListener {
            myService?.play()
        }

        btnStop?.setOnClickListener {
            myService?.stop()
        }

        receiver1 = Receiver1()
        val filter = IntentFilter()
        filter.addAction("UpdateActivity")
        registerReceiver(receiver1, filter)

        val intent = Intent(this, MyService2::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    inner class Receiver1 : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            status = intent.getIntExtra("status", -1)
            val current = intent.getIntExtra("current", -1)
            if (current >= 0) {
                tvTitle?.text = MyMusics.musics[current].title
                tvAuthor?.text = MyMusics.musics[current].author
            }

            when (status) {
                0x11 -> btnPlay?.setImageResource(R.drawable.play)
                0x12 -> btnPlay?.setImageResource(R.drawable.pause)
                0x13 -> btnPlay?.setImageResource(R.drawable.play)
            }
        }
    }
}
