package com.haste.lv.faith.module.videoplayer.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener;
import com.shuyu.gsyvideoplayer.video.GSYADVideoPlayer;

/**
 * Created by lv on 18-12-21.
 * 带广告的视频播放器, 播放器GSYADVideoPlayer和播放管理器GSYVideoADManager
 * 1.先播放广告，在播放视频内容
 * 2.可以重新定义广告的ui显示，如跳过按钮的，显示时间的等等
 */
public class ListADVideoPlayer extends GSYADVideoPlayer {
    public ListADVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public ListADVideoPlayer(Context context) {
        super(context);
    }

    public ListADVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void startPrepare() {
        GSYMediaPlayerListener listener = getGSYVideoManager().listener();
        super.startPrepare();
        if (listener != null) {
            listener.onAutoCompletion();
        }
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        if (!isCurrentMediaListener() && mVideoAllCallBack != null) {
            mVideoAllCallBack.onAutoComplete(mOriginUrl, mTitle, this);
        }
    }

    @Override
    public void onCompletion() {
        super.onCompletion();
    }
    public View getJumpAdView(){
        return mJumpAd;
    }
}
