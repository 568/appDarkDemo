package com.haste.lv.faith.mvpvm.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.ImmersionBar;
import com.haste.lv.faith.R;
import com.haste.lv.faith.uiviews.recyclerview.PullToRefreshRecyclerView;
import com.haste.lv.faith.uiviews.recyclerview.adapter.MultipleRecyclerViewAdapter;
import com.haste.lv.faith.uiviews.recyclerview.refresh.CustomRefreshHeader;

/**
 * Created by lv on 18-12-13.
 * 列表样式的容器
 * 1.默认页面会使用PullToRefreshRecyclerView+MultipleRecyclerViewAdapter
 */
public abstract class BaseListFragment<T extends BaseViewModel, VD extends MultipleRecyclerViewAdapter> extends BaseAbsLifecycleFragment<T> {
    protected PullToRefreshRecyclerView mRecyclerView;
    protected VD viewAdapter;
    protected View statusBarView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recyclerview_layout, container, false);
        statusBarView=view.findViewById(R.id.status_bar_height_view);
        mRecyclerView = view.findViewById(R.id.recyclerview_content);
        initRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract VD getViewAdapter();

    protected void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(getViewAdapter());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setFootViewText("努力加载中...","没有更多内容了～");
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader(getContext()));
        //mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    }

    @Override
    public void loadData(long id) {

    }

}
