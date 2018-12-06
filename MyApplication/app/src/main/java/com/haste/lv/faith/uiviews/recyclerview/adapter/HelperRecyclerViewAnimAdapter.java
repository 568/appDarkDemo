package com.haste.lv.faith.uiviews.recyclerview.adapter;

import android.animation.Animator;
import android.content.Context;
import android.view.animation.Interpolator;
import android.view.View;

import com.haste.lv.faith.uiviews.recyclerview.utils.AnimationType;
import com.haste.lv.faith.uiviews.recyclerview.utils.AnimationUtil;

import java.util.List;

/**
 * Created by lv on 18-12-6.
 * 继承于HelperRecyclerViewAdapter提供Itme动画效果便捷操作的baseAdapter
 */

public abstract class HelperRecyclerViewAnimAdapter<T>extends HelperRecyclerViewAdapter {
    private AnimationType mAnimationType;
    private int mAnimationDuration = 300;
    private boolean showItemAnimationEveryTime = false;
    private Interpolator mItemAnimationInterpolator;
    private CustomAnimator mCustomAnimator;
    private int mLastItemPosition = -1;

    public HelperRecyclerViewAnimAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public HelperRecyclerViewAnimAdapter(Context context, int... layoutIds) {
        super(context, layoutIds);
    }

    public HelperRecyclerViewAnimAdapter(Context context) {
        super(context);
    }

    @SuppressWarnings("deprecated")
    public HelperRecyclerViewAnimAdapter(List data, Context context) {
        super(data, context);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        addAnimation(holder);
    }

    protected final void addAnimation(final BaseRecyclerViewHolder holder) {
        int currentPosition = holder.getAdapterPosition();
        if (null != mCustomAnimator) {
            mCustomAnimator.getAnimator(holder.itemView).setDuration(mAnimationDuration).start();
        } else if (null != mAnimationType) {
            if (showItemAnimationEveryTime || currentPosition > mLastItemPosition) {
                new AnimationUtil()
                        .setAnimationType(mAnimationType)
                        .setTargetView(holder.itemView)
                        .setDuration(mAnimationDuration)
                        .setInterpolator(mItemAnimationInterpolator)
                        .start();
                mLastItemPosition = currentPosition;
            }
        }
    }

    /**
     * Animation api
     */
    public void setItemAnimation(AnimationType animationType) {
        mAnimationType = animationType;
    }

    public void setItemAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    public void setItemAnimationInterpolator(Interpolator animationInterpolator) {
        mItemAnimationInterpolator = animationInterpolator;
    }

    public void setShowItemAnimationEveryTime(boolean showItemAnimationEveryTime) {
        this.showItemAnimationEveryTime = showItemAnimationEveryTime;
    }

    public void setCustomItemAnimator(CustomAnimator customAnimator) {
        mCustomAnimator = customAnimator;
    }

    public interface CustomAnimator {
        Animator getAnimator(View itemView);
    }
}
