package com.haste.lv.faith.module.videoplayer.player;

import android.content.Context;
import android.util.AttributeSet;

import com.haste.lv.faith.module.videoplayer.views.CustomRenderView;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;

/**
 * Created by lv on 18-12-18.
 * 自定义渲染播放控件的播放器
 */

public class CustomRenderVideoPlayer extends NormalGSYVideoPlayer {
    public CustomRenderVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public CustomRenderVideoPlayer(Context context) {
        super(context);
    }

    public CustomRenderVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addTextureView() {
        mTextureView = new CustomRenderView();
        mTextureView.addView(getContext(), mTextureViewContainer, mRotate, this,
                this, mEffectFilter, mMatrixGL, mRenderer, mMode);
    }
}
