package com.haste.lv.faith.mvpvm.loadstatus;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lv on 18-12-12.
 * 状态页面的管理者
 * 使用介绍
 * 1.利用构造者模式创建LoadStateManager对象, setViewParams(StateViewParams svp)和setListener(OnViewRefreshListener orl)
 */
public class LoadStateManager {
    private LoadViewLayout loadLayout;

    public static class Builder {

        private StateViewParams stateViewParams;

        private BaseViewStateControl.OnViewRefreshListener onRefreshListener;

        public Builder setListener(BaseViewStateControl.OnViewRefreshListener listener) {
            this.onRefreshListener = listener;
            return this;
        }

        public Builder setViewParams(Object stateView) {
            this.stateViewParams = getViewParams(stateView);
            return this;
        }

        public LoadStateManager build() {
            return new LoadStateManager(stateViewParams, onRefreshListener, LoadState.newInstance().getBuilder());
        }
        public LoadStateManager build(LoadState.Builder stateBuilder){
            return new LoadStateManager(stateViewParams,onRefreshListener,stateBuilder);
        }

        public  StateViewParams getViewParams(Object targetView) {
            ViewGroup contentParent = null;
            Context context = null;
            View rootView = null;
            int rootViewIndex = 0;
            if (targetView != null) {
                if (targetView instanceof Activity) {
                    Activity activity = (Activity) targetView;
                    context = activity;
                    contentParent = activity.findViewById(android.R.id.content);
                    rootView = contentParent != null ? contentParent.getChildAt(0) : null;
                } else if (targetView instanceof View) {
                    rootView = (View) targetView;
                    contentParent = (ViewGroup) (rootView.getParent());
                    context = rootView.getContext();
                }
                int childCount = contentParent == null ? 0 : contentParent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (contentParent.getChildAt(i) == rootView) {
                        rootViewIndex = i;
                        break;
                    }
                }
                if (contentParent != null) {
                    contentParent.removeView(rootView);
                }
            } else {
                throw new IllegalArgumentException("The target must be  View");
            }
            return new StateViewParams(context, contentParent, rootView, rootViewIndex);
        }
    }


    public LoadStateManager(StateViewParams stateViewParams, BaseViewStateControl.OnViewRefreshListener onRefreshListener,
                       LoadState.Builder builder) {

        Context context = stateViewParams.context;
        View rootView = stateViewParams.rootView;
        ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
        loadLayout = new LoadViewLayout(context, onRefreshListener);
        loadLayout.setSuccessLayout(new SuccessViewStateControl (context,rootView,
                onRefreshListener));
        if (stateViewParams.parentView != null) {
            stateViewParams.parentView.addView(loadLayout, stateViewParams.childIndex, layoutParams);
        }
        initStateViews(builder);
    }

    private void initStateViews(LoadState.Builder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("The builder must be  set stateview");
        }
        /**
         * 获取储存的所有view
         */
        List<BaseViewStateControl> stateViews = builder.getStateViews();

        /**
         * 获取默认view
         */
        Class<? extends BaseViewStateControl> defalutStateView = builder.getDefaultStateView();
        if (stateViews != null && stateViews.size() > 0) {
            for (BaseViewStateControl stateView : stateViews) {
                loadLayout.setStateView(stateView);
            }
        }
        if (defalutStateView != null) {
            loadLayout.showStateView(defalutStateView);
        }
    }

    public void showSuccess() {
        loadLayout.showStateView(SuccessViewStateControl.class);
    }

    public boolean isShowSuccess(){
        return loadLayout.isShowSuccess();
    }
    public void showStateView(Class<? extends BaseViewStateControl> stateView) {
        loadLayout.showStateView(stateView);
    }

    public void showStateView(Class<? extends BaseViewStateControl> stateView, Object tag) {
        loadLayout.showStateView(stateView, tag);
    }

    public LoadViewLayout getLoadLayout() {
        return loadLayout;
    }

    public Class<? extends BaseViewStateControl> getCurrentStateView() {
        return loadLayout.getCurrentStateView();
    }

    public static class StateViewParams {
        public Context context;
        public ViewGroup parentView;
        public View rootView;
        public int childIndex;

        public StateViewParams(Context context, ViewGroup parentView, View rootView, int childIndex) {
            this.context = context;
            this.parentView = parentView;
            this.rootView = rootView;
            this.childIndex = childIndex;
        }
    }

}
