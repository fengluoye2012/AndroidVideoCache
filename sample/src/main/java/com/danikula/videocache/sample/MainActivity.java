package com.danikula.videocache.sample;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;

    private boolean isInitFinish;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = new MediaPlayer();

        setPlayVideo();
    }

    private void setPlayVideo() {
        String videoUrl = "https://assets.yyuehd.com/Fhev_4GB6fmylVxKvT60clrbPOCI";
        String proxyUrl = App.getProxy(act).getProxyUrl(videoUrl);

        //"http://127.0.0.1:38415/https%3A%2F%2Fassets.yyuehd.com%2FFhev_4GB6fmylVxKvT60clrbPOCI";
        LogUtils.i("proxyUrl：$proxyUrl");

        try {
            mediaPlayer.setDataSource(proxyUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setLooping(true);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isInitFinish = true;
                mp.start();
                LogUtils.i("初始化成功");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isInitFinish) {
            startPlay();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            stopPlay();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void startPlay() {
        //空判断
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }


    private void pausePlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void stopPlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }


    public static void gotoActivity(Context context) {
        ActivityUtils.startActivity(new Intent(context, MainActivity.class));
    }
}
