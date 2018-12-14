package com.haste.lv.faith.mvpvm.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lv on 18-12-12.
 * 视图层代理的接口协议
 */
public interface IViewDelegate {
    void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    View getRootView();

    void initWidget();
}
