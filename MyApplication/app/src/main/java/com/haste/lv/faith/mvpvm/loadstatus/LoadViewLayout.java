package com.haste.lv.faith.mvpvm.loadstatus;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lv on 18-12-12.
 * 装载各种状态的视图承载着，把自定义的状态页面添加到ViewGroup中
 */

public class LoadViewLayout extends FrameLayout {
    private Map<Class<? extends BaseViewStateControl>, BaseViewStateControl> stateViews = new HashMap<>();
    private Context context;
    private BaseViewStateControl.OnViewRefreshListener onRefreshListener;
    private Class<? extends BaseViewStateControl> preStateView;
    private Class<? extends BaseViewStateControl> curStateView;

    private boolean isShowSuccess = false;
    private static final int STATEVIEW_CUSTOM_INDEX = 1;

    public LoadViewLayout(@NonNull Context context) {
        super(context);
    }

    public LoadViewLayout(@NonNull Context context, BaseViewStateControl.OnViewRefreshListener refreshListener) {
        this(context);
        this.context = context;
        this.onRefreshListener = refreshListener;
    }

    public void setSuccessLayout(BaseViewStateControl baseStateControl) {
        addStateView(baseStateControl);
        View successView = baseStateControl.getRootView(null);
        successView.setVisibility(View.GONE);
        addView(successView);
        curStateView = SuccessViewStateControl.class;
    }

    public void setStateView(BaseViewStateControl stateview) {
        BaseViewStateControl cloneStateView = stateview.copy();
        cloneStateView.setStateView(context, null, onRefreshListener);
        addStateView(cloneStateView);
    }

    public void addStateView(BaseViewStateControl stateview) {
        if (!stateViews.containsKey(stateview.getClass())) {
            stateViews.put(stateview.getClass(), stateview);
        }
    }

    public void showStateView(final Class<? extends BaseViewStateControl> curStateView) {
        showStateView(curStateView, null);
    }

    public void showStateView(final Class<? extends BaseViewStateControl> curStateView, Object tag) {
        checkStateViewExist(curStateView);
        if (isMainThread()) {
            showStateViewView(curStateView, tag);
        } else {
            postMainThread(curStateView, tag);
        }
    }

    public Class<? extends BaseViewStateControl> getCurrentStateView() {
        return curStateView;
    }

    private void postMainThread(final Class<? extends BaseViewStateControl> status, final Object tag) {
        post(new Runnable() {
            @Override
            public void run() {
                showStateViewView(status, tag);
            }
        });
    }

    private void showStateViewView(Class<? extends BaseViewStateControl> status, Object tag) {
        isShowSuccess = false;
        if (preStateView != null) {
            if (preStateView == status) {
                return;
            }
            stateViews.get(preStateView).onDetach();
        }
        if (getChildCount() > 1) {
            removeViewAt(STATEVIEW_CUSTOM_INDEX);
        }

        for (Class key : stateViews.keySet()) {
            if (key == status) {
                SuccessViewStateControl successView = (SuccessViewStateControl) stateViews.get(SuccessViewStateControl.class);
                if (key == SuccessViewStateControl.class) {
                    isShowSuccess = true;
                    successView.show();
                } else {
                    successView.showWithStateView(stateViews.get(key).isVisible());
                    View rootView = stateViews.get(key).getRootView(tag);
                    addView(rootView);
                    stateViews.get(key).onAttach(context, rootView);
                }
                preStateView = status;
            }
        }
        curStateView = status;
    }

    private void checkStateViewExist(Class<? extends BaseViewStateControl> stateView) {
        if (!stateViews.containsKey(stateView)) {
            throw new IllegalArgumentException(String.format("The BaseStateControl (%s) is nonexistent.", stateView
                    .getSimpleName()));
        }
    }

    public boolean isShowSuccess() {
        return isShowSuccess;
    }

    public boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
