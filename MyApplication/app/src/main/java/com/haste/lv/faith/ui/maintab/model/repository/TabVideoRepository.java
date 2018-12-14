package com.haste.lv.faith.ui.maintab.model.repository;

import com.haste.lv.faith.mvpvm.base.BaseRepository;
import com.haste.lv.faith.network.eventbus.RxUtils;

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
    public static final String EVENT_KEY_MVD_LIST_STATE = "event_key_mvd_list_state";

    public TabVideoRepository() {

    }

    //网络请求方法，在ViewModel中调用，Retrofit+RxJava充当Repository，即可视为Model层
    public void requestNetWork() {
        //这里通过延迟模拟网络请求
        //addDisposable();
        Observable.just("").delay(5, TimeUnit.SECONDS)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        sendData(EVENT_KEY_MVD_LIST, "");
                    }
                });
    }
}
