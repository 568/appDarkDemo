package com.haste.lv.faith.ui.maintab;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;
import com.haste.lv.faith.mvpvm.base.BaseListFragment;
import com.haste.lv.faith.mvpvm.loadstatus.LoadState;
import com.haste.lv.faith.mvpvm.loadstatus.SuccessViewStateControl;
import com.haste.lv.faith.mvpvm.view.stateview.LoadingState;
import com.haste.lv.faith.ui.BaseLazyRxFragment;
import com.haste.lv.faith.ui.maintab.adapter.TabVideoAdapter;
import com.haste.lv.faith.ui.maintab.bean.VideoItem;
import com.haste.lv.faith.ui.maintab.model.repository.TabVideoRepository;
import com.haste.lv.faith.ui.maintab.viewmodel.TabVideoViewModel;
import com.haste.lv.faith.uiviews.recyclerview.PullToRefreshRecyclerView;

import java.util.ArrayList;

/**
 * Created by lv on 18-11-30.
 */

public class TabVideoFragment extends BaseListFragment<TabVideoViewModel,TabVideoAdapter> {
    @Override
    protected TabVideoAdapter getViewAdapter() {
        if (viewAdapter==null){
            viewAdapter=new TabVideoAdapter(new ArrayList<VideoItem>(),getContext());
        }
        return viewAdapter;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        mLoadStateManager.showStateView(LoadingState.class);
        mViewModel.getVideoListData();
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRecyclerView.setLoadingListener(new PullToRefreshRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mViewModel.getVideoListData();
            }

            @Override
            public void onLoadMore() {
                mViewModel.getVideoListData();
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(TabVideoRepository.EVENT_KEY_MVD_LIST,Object.class ).observe(this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
               if(mLoadStateManager.isShowSuccess()){
                   mRecyclerView.refreshComplete();
               }else{
                   mLoadStateManager.showSuccess();
               }
            }
        });
    }

    @Override
    public void loadData(long id) {
    }

    @Override
    public int getContentResId() {
        return R.id.recyclerview_content;
    }

    @Override
    protected boolean useLoadManager() {
        return true;
    }

}
