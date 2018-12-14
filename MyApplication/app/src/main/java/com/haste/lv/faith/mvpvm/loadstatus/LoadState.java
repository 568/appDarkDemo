package com.haste.lv.faith.mvpvm.loadstatus;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-12-12.
 * 加载状态的设定，使用的是单例模式
 */

public class LoadState {
    private static volatile LoadState loadState;

    private Builder builder;

    public static LoadState newInstance() {
        if (loadState == null) {
            synchronized (LoadState.class) {
                if (loadState == null) {
                    loadState = new LoadState();
                }
            }
        }
        return loadState;
    }

    public Builder getBuilder() {
        return builder;
    }

    private LoadState() {
        this.builder = new Builder();
    }

    public void setBuilder(@NonNull Builder builder) {
        this.builder = builder;
    }

    public static class Builder {

        private List<BaseViewStateControl> stateViews = new ArrayList<>();

        private Class<? extends BaseViewStateControl> defaultStateView;

        /**
         * 添加不同状态页面
         *
         * @param stateView
         * @return
         */
        public Builder register(@NonNull BaseViewStateControl stateView) {
            stateViews.add(stateView);
            return this;
        }

        /**
         * 设置默认现实状态UI
         *
         * @param defaultStateView
         * @return
         */
        public Builder setDefaultStateView(@NonNull Class<? extends BaseViewStateControl> defaultStateView) {
            this.defaultStateView = defaultStateView;
            return this;
        }

        List<BaseViewStateControl> getStateViews() {
            return stateViews;
        }

        Class<? extends BaseViewStateControl> getDefaultStateView() {
            return defaultStateView;
        }

        public void build() {
            newInstance().setBuilder(this);
        }


    }
}
