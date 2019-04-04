package com.example.myapplication.musicdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.musicdemo.data.MyMusics


class MusicAvtivity : AppCompatActivity() {

    private var tvTitle: TextView? = null
    private var tvAuthor: TextView? = null
    private var btnPlay: ImageButton? = null
    private var btnStop: ImageButton? = null

    private var receiver1: Receiver1? = null

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
            val intent = Intent("UpdateService")
            intent.putExtra("command", 1)
            sendBroadcast(intent)
        }

        btnStop?.setOnClickListener {
            val intent = Intent("UpdateService")
            intent.putExtra("command", 2)
            sendBroadcast(intent)
        }

        receiver1 = Receiver1()
        val filter = IntentFilter()
        filter.addAction("UpdateActivity")
        registerReceiver(receiver1, filter)

        val intent = Intent(this, MyService::class.java)
        startService(intent)
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
