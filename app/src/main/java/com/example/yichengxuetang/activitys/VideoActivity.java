package com.example.yichengxuetang.activitys;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.utils.MyJieCaoVideoView;
import com.llw.mvplibrary.network.utils.StatusBarUtils;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class VideoActivity extends AppCompatActivity {
    private MyJieCaoVideoView playerStandard;
    private String videoUrl = "http://rbv01.ku6.com/omtSn0z_PTREtneb3GRtGg.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
    }
    private void init() {
        StatusBarUtils.setTransparent(this);
       // StatusBarUtils.setColor(this,R.color.black);
        StatusBarUtils.setTextDark(this,false);
        playerStandard =  findViewById(R.id.playerstandard);
        playerStandard.setUp(videoUrl,MyJieCaoVideoView.SCREEN_LAYOUT_NORMAL,"追龙");
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        JCVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向
        playerStandard.startVideo();
    }

    @Override
    public void onBackPressed() {
        if (playerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        playerStandard.releaseAllVideos();
    }
}