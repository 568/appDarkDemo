package com.haste.lv.faith.uiviews.recyclerview.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haste.lv.faith.R;

/**
 * Created by lv on 18-12-14.
 * 自定义动画
 * 1.单独把动画效果这块抽离出来，方便结合其他模块使用，如果动画效果变了可以仿照这个重新定义
 */
public class CustomAnimView extends LinearLayout {
    private ImageView mImageView;
    private AnimationDrawable mAnimationDrawable;

    public CustomAnimView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        View header = LayoutInflater.from(context).inflate(R.layout.clife_loading_header2, this);
        mImageView = header.findViewById(R.id.pull_to_refresh_image);
        mImageView.setImageResource(R.drawable.clife_refresh_loading);
        mAnimationDrawable = (AnimationDrawable) mImageView.getDrawable();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    public void startAnim() {
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    public void stopAnim() {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }
}
