package com.haste.lv.faith.ui.video;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.view.Window;

import com.haste.lv.faith.R;
import com.haste.lv.faith.module.videoplayer.listener.OnTransitionListener;
import com.haste.lv.faith.module.videoplayer.model.SwitchVideoModel;
import com.haste.lv.faith.module.videoplayer.player.SmartPickVideo;
import com.haste.lv.faith.mvpvm.base.BaseAbsLifecycleActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-12-18.
 * 全屏显示，支持筛选视频源
 */

public class VideoFullScreenActivity extends BaseAbsLifecycleActivity{
    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";

    private SmartPickVideo smartPickVideo;
    OrientationUtils orientationUtils;

    private boolean isTransition;

    private Transition transition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_fullscreen);
        isTransition = getIntent().getBooleanExtra(TRANSITION, false);
        initviews();
    }

    private void initviews() {
        smartPickVideo=findViewById(R.id.video_player_smartpick_view);
        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String name = "普通";
        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name, source1);

        String source2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
        String name2 = "清晰";
        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, source2);

        List<SwitchVideoModel> list = new ArrayList<>();
        list.add(switchVideoModel);
        list.add(switchVideoModel2);
        smartPickVideo.setUp(list, false, "");
        //设置返回键
        smartPickVideo.getBackButton().setVisibility(View.GONE);

        //设置旋转
        orientationUtils = new OrientationUtils(this, smartPickVideo);

        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        smartPickVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });

        //是否可以滑动调整
        smartPickVideo.setIsTouchWiget(true);

        //设置返回按键功能
        smartPickVideo.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //过渡动画
        initTransition();
    }

    private void initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(smartPickVideo, IMG_TRANSITION);
            addTransitionListener();
            startPostponedEnterTransition();
        } else {
            smartPickVideo.startPlayLogic();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            transition.addListener(new OnTransitionListener(){
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    smartPickVideo.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            smartPickVideo.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        smartPickVideo.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onBackPressed();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }, 500);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        smartPickVideo.onVideoPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        smartPickVideo.onVideoResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        smartPickVideo.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
    }
}
