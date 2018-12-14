package com.haste.lv.faith.mvpvm.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.haste.lv.faith.core.HFBaseActivity;
import com.haste.lv.faith.mvpvm.ClassUtil;
import com.haste.lv.faith.mvpvm.loadstatus.BaseViewStateControl;
import com.haste.lv.faith.mvpvm.loadstatus.LoadStateManager;
import com.haste.lv.faith.mvpvm.model.viewmodel.AbsViewModel;
import com.haste.lv.faith.mvpvm.view.stateview.ErrorState;
import com.haste.lv.faith.mvpvm.view.stateview.LoadingState;
import com.haste.lv.faith.mvpvm.view.stateview.StateConstants;
import com.haste.lv.faith.network.eventbus.LiveBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-12-12.
 */
public class AbsLifecycleActivity<T extends AbsViewModel> extends HFBaseActivity {
    protected LoadStateManager mLoadStateManager;
    protected T mViewModel;
    public AbsLifecycleActivity(){
        //无参构造方法放在第一位，因为在反射实例化的时候设定的默认参数
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useLoadManager()) {
            mLoadStateManager = new LoadStateManager.Builder()
                    .setViewParams(this)
                    .setListener(new BaseViewStateControl.OnViewRefreshListener() {
                        @Override
                        public void onRefresh(View v) {
                            onStateRefresh();
                        }
                    })
                    .build();
        }
    }

    /**
     * 初始化views
     *
     * @param savedInstanceState
     */
    public void initViewModel(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) ClassUtil.getInstance(this, 0));
        dataObserver();
    }
    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);

    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, Class<T> tClass) {

        return registerObserver(eventKey, null, tClass);
    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, String tag, Class<T> tClass) {
        String event;
        if (TextUtils.isEmpty(tag)) {
            event = (String) eventKey;
        } else {
            event = eventKey + tag;
        }
        eventKeys.add(event);
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
    }

    protected void showError(Class<? extends BaseViewStateControl> stateView, Object tag) {
        mLoadStateManager.showStateView(stateView, tag);
    }

    protected void showError(Class<? extends BaseViewStateControl> stateView) {
        showError(stateView, null);
    }

    protected void showSuccess() {
        mLoadStateManager.showSuccess();
    }

    protected void showLoading() {
        mLoadStateManager.showStateView(LoadingState.class);
    }

    protected void dataObserver() {

    }
    /**
     * 页面状态的监听处理逻辑
     * eg:
     * 1.Repository获取数据后通过showPageState()方法返回状态码，
     * 2.observer响应结果，根据状态结果设置页面的显示(showLoading(),showSuccess(),showError())
     * */
    protected void setObserver(String mStateEventKey,Observer observer){
        if (mViewModel!=null){
            eventKeys.add(mStateEventKey);
            if (null==observer){
                observer = new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String state) {
                        if (!TextUtils.isEmpty(state)) {
                            if (StateConstants.ERROR_STATE.equals(state)) {
                                showError(ErrorState.class, "2");
                            } else if (StateConstants.NET_WORK_STATE.equals(state)) {
                                showError(ErrorState.class, "1");
                            } else if (StateConstants.LOADING_STATE.equals(state)) {
                                showLoading();
                            } else if (StateConstants.SUCCESS_STATE.equals(state)) {
                                showSuccess();
                            }
                        }
                    }
                };
            }
            LiveBus.getDefault().subscribe(mStateEventKey).observe(this, observer);
        }
    }
    /**
     *状态页面的点击事件
     */
    protected void onStateRefresh() {

    }
    /**
     *是否使用LoadManager
     */
    protected boolean useLoadManager() {
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventKeys != null && eventKeys.size() > 0) {
            for (int i = 0; i < eventKeys.size(); i++) {
                LiveBus.getDefault().clear(eventKeys.get(i));
            }
        }
    }

    private List<Object> eventKeys = new ArrayList<>();
}
