package com.haste.lv.faith.ui.maintab.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.haste.lv.faith.mvpvm.base.BaseViewModel;
import com.haste.lv.faith.ui.maintab.model.repository.TabVideoRepository;

/**
 * Created by lv on 18-12-13.
 * 主页tab---视频流viewmodel
 */
public class TabVideoViewModel extends BaseViewModel<TabVideoRepository> {
    public TabVideoViewModel(@NonNull Application application) {
        super(application);
    }
    public void getVideoListData(){
        mRepository.requestNetWork();
    }
}
