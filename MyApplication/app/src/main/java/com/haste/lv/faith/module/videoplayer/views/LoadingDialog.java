package com.haste.lv.faith.module.videoplayer.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haste.lv.faith.R;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.AVLoadingIndicatorView;
import com.haste.lv.faith.uiviews.recyclerview.refresh.ProgressStyle;
import com.haste.lv.faith.uiviews.recyclerview.refresh.SimpleViewSwitcher;

/**
 * Created by lv on 18-12-20.
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.refresh_view_default_loading_layout, null);
        SimpleViewSwitcher mProgressBar = view.findViewById(R.id.refreshview_loading_progressbar);
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(getContext());
        progressView.setIndicatorColor(0xff3cd3db);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        mProgressBar.setView(progressView);
        TextView textView = view.findViewById(R.id.forum_desc_text);
        textView.setText("正在切换中...");
        setContentView(view);

        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }
}
