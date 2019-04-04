package com.example.myapplication.musicdemo2;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.example.myapplication.musicdemo.data.MyMusics;

import java.io.IOException;

public class MyService2 extends Service {

    AssetManager am;
    MediaPlayer mPlayer;
    int status = 0x11;
    int current = 0;
    MyBinder myBinder = null;

    @Override
    public void onCreate() {
        myBinder = new MyBinder();
        am = getAssets();
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                current++;
                if (current > 3) {
                    current = 0;
                }

                prepareAndPlay(MyMusics.musics[current].name);
                sendMessageToActivity(-1, current);
            }
        });
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        myBinder = null;
        return super.onUnbind(intent);
    }

    private class MyBinder extends Binder implements IServiceInterface {

        @Override
        public void play() {
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
            sendMessageToActivity(status, current);
        }

        @Override
        public void stop() {
            if (status == 0x12 || status == 0x13) {
                mPlayer.stop();
                status = 0x11;
            }
            sendMessageToActivity(status, current);
        }
    }

    private void sendMessageToActivity(int status, int current) {
        Intent sendIntent = new Intent("UpdateActivity");
        sendIntent.putExtra("status", status);
        sendIntent.putExtra("current", current);
        sendBroadcast(sendIntent);
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
}
