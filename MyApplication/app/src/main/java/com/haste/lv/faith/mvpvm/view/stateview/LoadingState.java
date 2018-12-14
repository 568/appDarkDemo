package com.haste.lv.faith.mvpvm.view.stateview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;
import com.haste.lv.faith.mvpvm.loadstatus.BaseViewStateControl;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.AVLoadingIndicatorView;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.AVLoadingIndicatorView2;
import com.haste.lv.faith.uiviews.recyclerview.refresh.ProgressStyle;
import com.haste.lv.faith.uiviews.recyclerview.refresh.SimpleViewSwitcher;

/**
 * Created by lv on 18-12-12.
 * 默认的页面加载状态的视图
 */
public class LoadingState extends BaseViewStateControl {
    @Override
    protected int onCreateView() {
        return R.layout.refresh_view_default_loading_layout;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);
        SimpleViewSwitcher mProgressBar = view.findViewById(R.id.refreshview_loading_progressbar);
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(context);
        progressView.setIndicatorColor(0xff3cd3db);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        mProgressBar.setView(progressView);
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }
}
