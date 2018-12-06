package com.haste.lv.faith.uiviews.recyclerview.refresh;

import android.view.View;

/**
 * Created by lv on 18-12-6.
 * 自定义刷新动画的接口
 */

public interface IRefreshHeader {
    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();

    boolean isRefreshHreader();

    /**
     * 设置动画样式
     * @param style
     */
    void setProgressStyle(int style);

    /**
     * 设置动画的三角箭头  没有就不用处理
     * @param resid
     */
    void setArrowImageView(int resid);

    /**
     * 设置状态
     * @param state
     */
    void setState(int state);

    /**
     * 获取头部动画当前的转态
     * @return
     */
    int getState();

    /**
     * 获取头部view的高度
     * @return
     */
    int getVisibleHeight();

    /**
     * 返回当前自定义头部对象 this
     * @return
     */
    View getHeaderView();
}
