package com.haste.lv.faith.ui.video;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.view.Window;

import com.haste.lv.faith.R;
import com.haste.lv.faith.module.videoplayer.listener.OnTransitionListener;
import com.haste.lv.faith.module.videoplayer.model.SwitchVideoModel;
import com.haste.lv.faith.module.videoplayer.model.VideoVO;
import com.haste.lv.faith.module.videoplayer.player.SmartPickVideo;
import com.haste.lv.faith.mvpvm.base.BaseAbsLifecycleActivity;
import com.haste.lv.faith.ui.maintab.adapter.TabVideoAdapter;
import com.haste.lv.faith.ui.video.viewmodel.AdvertVideoViewModel;
import com.haste.lv.faith.uiviews.recyclerview.PullToRefreshRecyclerView;
import com.haste.lv.faith.uiviews.recyclerview.refresh.CustomRefreshHeader;
import com.shuyu.gsyvideoplayer.GSYVideoADManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-12-18.
 * 带广告播放列表，支持中间插入广告模式
 */

public class VideoListActivity extends BaseAbsLifecycleActivity<AdvertVideoViewModel>{
    protected PullToRefreshRecyclerView mRecyclerView;
    protected TabVideoAdapter viewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        initviews();
    }

    private void initviews() {
        if (viewAdapter == null) {
            viewAdapter = new TabVideoAdapter(new ArrayList<VideoVO>(), this);
        }
        mRecyclerView = findViewById(R.id.recyclerview_content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(viewAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setFootViewText("努力加载中...","没有更多内容了～");
        mRecyclerView.setRefreshHeader(new CustomRefreshHeader(this));

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
                    if ((GSYVideoManager.instance().getPlayTag().equals("ListADNormalAdapter"))
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //释放广告和视频
                        if (GSYVideoADManager.instance().listener() != null) {
                            GSYVideoADManager.instance().listener().onAutoCompletion();
                        }
                        GSYVideoADManager.releaseAllVideos();
                        GSYVideoManager.releaseAllVideos();
                        viewAdapter.notifyDataSetChanged();
                    }
                }

                /*else if (GSYVideoADManager.instance().getPlayPosition()>=0){
                    //当前播放的位置
                    int position = GSYVideoADManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if ((GSYVideoADManager.instance().getPlayTag().equals("ListADNormalAdapter"))
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //释放广告和视频
                        if (GSYVideoADManager.instance().listener() != null) {
                            GSYVideoADManager.instance().listener().onAutoCompletion();
                        }
                        GSYVideoADManager.releaseAllVideos();
                        GSYVideoManager.releaseAllVideos();
                        viewAdapter.notifyDataSetChanged();
                    }
                }*/
            }
        });

        //数据模拟
        mViewModel.requestDataFormNetWork();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        mViewModel.getCurrentVideoData().observe(this, new Observer<List<VideoVO>>() {
            @Override
            public void onChanged(@Nullable List<VideoVO> videoVOS) {
                if (videoVOS!=null){
                    viewAdapter.addItemsToLast(videoVOS);
                    mRecyclerView.refreshComplete();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoADManager.backFromWindowFull(this)) {
            return;
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
        GSYVideoADManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
        GSYVideoADManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        GSYVideoADManager.releaseAllVideos();
    }
}
