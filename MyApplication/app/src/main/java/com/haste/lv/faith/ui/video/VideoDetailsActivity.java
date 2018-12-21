package com.haste.lv.faith.ui.video;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.view.Window;

import com.haste.lv.faith.R;
import com.haste.lv.faith.module.videoplayer.model.SwitchVideoModel;
import com.haste.lv.faith.module.videoplayer.player.PreViewGSYVideoPlayer;
import com.haste.lv.faith.module.videoplayer.player.SmartPickVideo;
import com.haste.lv.faith.module.videoplayer.player.StandardControlVideoPlayer;
import com.haste.lv.faith.mvpvm.base.BaseAbsLifecycleActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-12-18.
 * 1.视频播放页面，单独视频
 * 2.可以带预览的播放
 */
public class VideoDetailsActivity extends BaseAbsLifecycleActivity implements View.OnClickListener{
    private PreViewGSYVideoPlayer preViewGSYVideoPlayer;
    private StandardControlVideoPlayer standardControlVideoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        initviews();
    }
    private void initviews(){
        preViewGSYVideoPlayer=findViewById(R.id.video_player_view);
        findViewById(R.id.btn_full).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
        //可以滑动查看预览的播放器
        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        preViewGSYVideoPlayer.setUp(url,true,"");
        preViewGSYVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preViewGSYVideoPlayer.startWindowFullscreen(VideoDetailsActivity.this,true,true);
            }
        });

        //旋转镜像，旋转画面，画面比例的播放器
        standardControlVideoPlayer=findViewById(R.id.video_player_control_view);
        standardControlVideoPlayer.setUp(url,true,"");
        //
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return ;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        preViewGSYVideoPlayer.onVideoPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preViewGSYVideoPlayer.onVideoResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preViewGSYVideoPlayer.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
    }


    @Override
    public void onClick(View v) {
        final int id= v.getId();
        if (id==R.id.btn_full){//全屏播放，可以调整选择视频清晰度的播放器
            startActivity(VideoFullScreenActivity.class);
        }else  if (id==R.id.btn_list){//视频列表播放，带有片头广告的播放器
            startActivity(VideoListActivity.class);
        }
    }
}
