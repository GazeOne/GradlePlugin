package com.example.myapplication.musicdemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.example.myapplication.musicdemo.data.MyMusics;

import java.io.IOException;

public class MyService extends Service {

    Receiver2 receiver2;
    AssetManager am;
    MediaPlayer mPlayer;
    int status = 0x11;
    int current = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        am = getAssets();

        receiver2 = new Receiver2();
        IntentFilter filter = new IntentFilter("UpdateService");
        registerReceiver(receiver2, filter);

        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                current++;
                if (current > 3) {
                    current = 0;
                }

                prepareAndPlay(MyMusics.musics[current].name);
                Intent sendIntent = new Intent("UpdateActivity");
                sendIntent.putExtra("status", -1);
                sendIntent.putExtra("current", current);
                sendBroadcast(sendIntent);
            }
        });
        super.onCreate();
    }

    private void prepareAndPlay(String name) {
        try {
            AssetFileDescriptor afd = am.openFd(name);
            mPlayer.reset();
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Receiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int command = intent.getIntExtra("command", -1);
            switch (command) {
                case 1:
                    if (status == 0x11) {
                        prepareAndPlay(MyMusics.musics[current].name);
                        status = 0x12;
                    } else if (status == 0x12) {
                        mPlayer.pause();
                        status = 0x13;
                    } else if (status == 0x13) {
                        mPlayer.start();
                        status = 0x12;
                    }
                    break;
                case 2:
                    if (status == 0x12 || status == 0x13) {
                        mPlayer.stop();
                        status = 0x11;
                    }
                    break;
            }

            Intent intent1 = new Intent("UpdateActivity");
            intent1.putExtra("status", status);
            intent1.putExtra("current", current);
            sendBroadcast(intent1);
        }
    }
}
