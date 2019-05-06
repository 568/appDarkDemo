package com.haste.lv.faith.ui.maintab;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.ImmersionBar;
import com.haste.lv.faith.R;
import com.haste.lv.faith.module.videoplayer.model.VideoVO;
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
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-11-30.
 */

public class TabVideoFragment extends BaseListFragment<TabVideoViewModel, TabVideoAdapter> {

    @Override
    protected TabVideoAdapter getViewAdapter() {
        if (viewAdapter == null) {
            viewAdapter = new TabVideoAdapter(new ArrayList<VideoVO>(), getContext());
        }
        return viewAdapter;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        mLoadStateManager.showStateView(LoadingState.class);
        mViewModel.getVideoListData();
        //设置显示比例
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_4_3);
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
                mViewModel.loadMoreData((int) (Math.random() * (101 - 1 + 1)));
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals("VideoRecyclerViewList")
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if (!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            viewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });


    }

    @Override
    protected void dataObserver() {
        registerObserver(TabVideoRepository.EVENT_KEY_MVD_LIST, VideoItem.class).observe(this, new Observer<VideoItem>() {
            @Override
            public void onChanged(@Nullable VideoItem videoItems) {
                if (videoItems != null)
                    viewAdapter.setListAll(videoItems.videoData);
                if (mLoadStateManager.isShowSuccess()) {
                    mRecyclerView.refreshComplete();
                } else {
                    mLoadStateManager.showSuccess();
                }
            }
        });
        registerObserver(TabVideoRepository.EVENT_KEY_MVD_MORE_LIST, VideoItem.class).observe(this, new Observer<VideoItem>() {
            @Override
            public void onChanged(@Nullable VideoItem videoItem) {
                if (videoItem != null) {
                    viewAdapter.addItemsToLast(videoItem.videoData);
                    mRecyclerView.loadMoreComplete();
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });
    }

    public boolean onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
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

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .statusBarView(statusBarView)
                .init();
    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (immersionBarEnabled()){
            initImmersionBar();
        }
    }
}
