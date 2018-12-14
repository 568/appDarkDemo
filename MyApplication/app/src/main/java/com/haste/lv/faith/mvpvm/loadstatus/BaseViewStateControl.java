package com.haste.lv.faith.mvpvm.loadstatus;

import android.content.Context;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by lv on 18-12-12.
 */

public abstract class BaseViewStateControl implements Serializable {
    private View rootView;
    private Context context;
    private OnViewRefreshListener onViewRefreshListener;
    private boolean isVisible;

    public BaseViewStateControl() {
    }

    public BaseViewStateControl(Context context, View view, OnViewRefreshListener refreshListener) {
        this.onViewRefreshListener = refreshListener;
        this.rootView = view;
        this.context = context;
    }

    public BaseViewStateControl setStateView(Context context, View view, OnViewRefreshListener refreshListener) {
        this.onViewRefreshListener = refreshListener;
        this.rootView = view;
        this.context = context;
        return this;
    }

    public View getRootView(Object tag) {
        int resId = onCreateView();
        if (resId == 0 && rootView != null) {
            return rootView;
        }

        if (onBuildView(context) != null) {
            rootView = onBuildView(context);
        }

        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null);
        }
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadEvent(context, rootView)) {
                    return;
                }
                if (onViewRefreshListener != null) {
                    onViewRefreshListener.onRefresh(v);
                }
            }
        });

        if (tag != null) {
            rootView.setTag(null);
            rootView.setTag(tag);
        }

        onViewCreate(context, rootView);
        return rootView;
    }

    public View getRootView() {
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null);
        }
        return rootView;
    }


    public interface OnViewRefreshListener extends Serializable {
        void onRefresh(View v);
    }

    protected abstract int onCreateView();

    protected void onViewCreate(Context context, View view) {
    }

    public void onAttach(Context context, View view) {
    }

    public void onDetach() {
    }

    protected View onBuildView(Context context) {
        return null;
    }

    public boolean isVisible() {
        return isVisible;
    }

    void isVisible(boolean visible) {
        this.isVisible = visible;
    }

    protected boolean onRetry(Context context, View view) {
        return false;
    }

    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }

    /**
     * 拷贝
     */
    public BaseViewStateControl copy() {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        Object obj = null;
        try {
            oos = new ObjectOutputStream(bao);
            oos.writeObject(this);
            oos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (BaseViewStateControl) obj;
    }
}
