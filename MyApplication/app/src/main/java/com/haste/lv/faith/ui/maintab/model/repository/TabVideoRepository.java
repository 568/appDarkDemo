package com.haste.lv.faith.ui.maintab.model.repository;

import com.haste.lv.faith.module.videoplayer.model.VideoVO;
import com.haste.lv.faith.mvpvm.base.BaseRepository;
import com.haste.lv.faith.network.eventbus.RxUtils;
import com.haste.lv.faith.ui.maintab.bean.VideoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lv on 18-12-13.
 * 主页tab--视频列表的数据获取
 */
public class TabVideoRepository extends BaseRepository {
    public static final String EVENT_KEY_MVD_LIST = "event_key_mvd_list";
    public static final String EVENT_KEY_MVD_MORE_LIST = "event_key_mvd_more_list";
    public static final String EVENT_KEY_MVD_LIST_STATE = "event_key_mvd_list_state";

    public TabVideoRepository() {

    }

    public void requestNetWork(final int id) {
        addDisposable(Observable.just("").delay(3, TimeUnit.SECONDS)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        if (id%2==0) {
                            VideoItem videoItem = new VideoItem();
                            List<VideoVO> dataList = videoItem.videoData;
                            for (int i = 0; i < 20; i++) {
                                VideoVO item = new VideoVO();
                                item.title = "bibibibibibibibibibi";
                                if (i % 2 == 0)
                                    item.videoUrl = "https://res.exexm.com/cw_145225549855002";
                                else if (i%3==0)
                                    item.videoUrl="http://gv.static.nextjoy.com/article/video/26779803.flv.mp4";
                                else
                                    item.videoUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
                                dataList.add(item);
                            }
                            sendData(EVENT_KEY_MVD_MORE_LIST, videoItem);
                        }else{
                            sendData(EVENT_KEY_MVD_MORE_LIST, null);
                        }
                    }
                }));
    }
    //网络请求方法，在ViewModel中调用，Retrofit+RxJava充当Repository，即可视为Model层
    public void requestNetWork() {
        //这里通过延迟模拟网络请求
        addDisposable(Observable.just("").delay(3, TimeUnit.SECONDS)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        VideoItem videoItem = new VideoItem();
                        List<VideoVO> dataList = videoItem.videoData;
                        for (int i = 0; i < 20; i++) {
                            VideoVO item = new VideoVO();
                            item.title = "bibibibibibibibibibi";
                            if (i % 2 == 0)
                                item.videoUrl = "https://res.exexm.com/cw_145225549855002";
                            else if (i%3==0)
                                item.videoUrl="http://gv.static.nextjoy.com/article/video/26779803.flv.mp4";
                            else
                                item.videoUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
                            dataList.add(item);
                        }
                        sendData(EVENT_KEY_MVD_LIST, videoItem);
                    }
                }));
    }
}
