package com.haste.lv.faith.ui.video.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.haste.lv.faith.module.videoplayer.model.VideoVO;
import com.haste.lv.faith.mvpvm.base.BaseViewModel;
import com.haste.lv.faith.network.eventbus.RxBusSubscriber;
import com.haste.lv.faith.network.eventbus.RxUtils;
import com.haste.lv.faith.ui.video.repository.VideoADRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-12-21.
 * 带广告片头的视频列表的viewmodel(VideoListActivity)
 */

public class AdvertVideoViewModel extends BaseViewModel<VideoADRepository> {
    private MutableLiveData<List<VideoVO>> currentVideoData;

    public MutableLiveData<List<VideoVO>> getCurrentVideoData() {
        if (currentVideoData==null){
            currentVideoData=new MutableLiveData<>();
        }
        return currentVideoData;
    }

    public AdvertVideoViewModel(@NonNull Application application) {
        super(application);
    }

    public void requestDataFormNetWork() {
        mRepository.getVideoList()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .subscribe(new RxBusSubscriber<String>() {
                    @Override
                    protected void onEvent(String str) {
                        List<VideoVO> dataList = new ArrayList<>();
                        for (int i = 0; i < 20; i++) {
                            VideoVO item = new VideoVO();
                            item.styleType=101;
                            item.title = "ggggggggggghhhh";
                            if (i % 2 == 0) {
                                item.videoUrl = "https://res.exexm.com/cw_145225549855002";
                            }else if (i%3==0) {
                                item.videoUrl = "http://gv.static.nextjoy.com/article/video/26779803.flv.mp4";
                                item.advertUrl="http://video.7k.cn/app_video/20171202/6c8cf3ea/v.m3u8.mp4";
                                item.isNeedAdOnStart=true;
                            }else if (i%5==0) {
                                item.videoUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
                                item.advertUrl="http://video.7k.cn/app_video/20171202/6c8cf3ea/v.m3u8.mp4";
                                item.isNeedAdOnStart=true;
                            }else {
                                item.videoUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
                            }
                            item.advertUrl="http://video.7k.cn/app_video/20171202/6c8cf3ea/v.m3u8.mp4";
                            item.isNeedAdOnStart=true;
                            dataList.add(item);
                        }
                        getCurrentVideoData().setValue(dataList);
                    }
                });
    }
}
