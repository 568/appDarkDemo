package com.haste.lv.faith.ui.maintab.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.haste.lv.faith.ui.maintab.bean.SettingItem;

import java.util.List;

/**
 * Created by lv on 18-12-7.
 */

public class SettingViewModel extends ViewModel {
    private MutableLiveData<SettingItem> mCurrentData;
    private MutableLiveData<List<SettingItem>> mSettingListData;
    public MutableLiveData<SettingItem> getCurrentSettingData(){
        if (null==mCurrentData){
            mCurrentData=new MutableLiveData<>();
        }
        return mCurrentData;
    }
    public MutableLiveData<List<SettingItem>> getSettingListData(){
        if (null==mSettingListData){
            mSettingListData=new MutableLiveData<>();
        }
        return mSettingListData;
    }
}
