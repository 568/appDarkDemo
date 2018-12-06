package com.haste.lv.faith.uiviews.recyclerview.refresh;

import android.view.View;

/**
 * Created by lv on 18-12-6.
 * 自定义加载更多的接口
 */

public interface IMoreFooter {
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    public void setLoadingHint(String hint);

    public void setNoMoreHint(String hint);

    public void setLoadingDoneHint(String hint);

    public void setProgressStyle(int style);

    public boolean isLoadingMore();

    public void setState(int state);

    /**
     * 返回当前自定义更多对象 this
     *
     * @return
     */
    View getFooterView();
}
