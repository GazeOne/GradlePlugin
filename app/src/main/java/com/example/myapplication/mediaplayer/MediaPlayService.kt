package com.example.myapplication.mediaplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import java.io.IOException

class MediaPlayService : Service() {

    var mPlayer: MediaPlayer? = null
    var myBinder: MediaBinder? = null
    var status = 0x11
    var current = 0
    val list = mutableListOf<String>(
        "http://music.163.com/song/media/outer/url?id=317151.mp3",
        "http://music.163.com/song/media/outer/url?id=281951.mp3",
        "http://music.163.com/song/media/outer/url?id=317153.mp3",
        "http://music.163.com/song/media/outer/url?id=281953.mp3",

        "http://music.163.com/song/media/outer/url?id=317154.mp3",
        "http://music.163.com/song/media/outer/url?id=317155.mp3",
        "http://music.163.com/song/media/outer/url?id=317156.mp3",
        "http://music.163.com/song/media/outer/url?id=317157.mp3",
        "http://music.163.com/song/media/outer/url?id=317158.mp3",
        "http://music.163.com/song/media/outer/url?id=317159.mp3",
        "http://music.163.com/song/media/outer/url?id=317152.mp3",
        "http://music.163.com/song/media/outer/url?id=317150.mp3"
    )

    override fun onCreate() {
        myBinder = MediaBinder()
        mPlayer = MediaPlayer()
        mPlayer?.setOnCompletionListener {
            current++
            if (current > 10) {
                current = 0
            }

            prepareAndPlay(list[current])
            sendMessageToActivity(-1, current)
        }
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        return myBinder!!
    }


    private fun prepareAndPlay(name: String) {
        try {

            mPlayer?.reset()
            mPlayer?.setDataSource(name)
            mPlayer?.prepare()
            mPlayer?.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    inner class MediaBinder : Binder(), IServiceInterface {

        override fun next() {
            prepareAndPlay(list[++current])
        }

        override fun play() {
            if (status == 0x11) {
                prepareAndPlay(list[current])
                status = 0x12
            } else if (status == 0x12) {
                mPlayer?.pause()
                status = 0x13
            } else if (status == 0x13) {
                mPlayer?.start()
                status = 0x12
            }
            sendMessageToActivity(status, current)
        }

        override fun stop() {
            if (status == 0x12 || status == 0x13) {
                mPlayer?.stop()
                status = 0x11
            }
            sendMessageToActivity(status, current)
        }
    }

    private fun sendMessageToActivity(status: Int, current: Int) {
        val sendIntent = Intent("MediaPlayerActivity")
        sendIntent.putExtra("status", status)
        sendIntent.putExtra("current", current)
        sendBroadcast(sendIntent)
    }

}
