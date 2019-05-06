package com.haste.lv.faith.mvpvm.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.haste.lv.faith.mvpvm.ClassUtil;
import com.haste.lv.faith.mvpvm.loadstatus.BaseViewStateControl;
import com.haste.lv.faith.mvpvm.model.viewmodel.AbsViewModel;
import com.haste.lv.faith.mvpvm.view.stateview.ErrorState;
import com.haste.lv.faith.mvpvm.view.stateview.LoadingState;
import com.haste.lv.faith.mvpvm.view.stateview.StateConstants;
import com.haste.lv.faith.network.eventbus.LiveBus;
import com.haste.lv.faith.ui.BaseLazyRxFragment;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-12-12.
 * 1.可以实例化ViewModel，数据进行了绑定，可以监听到change
 * -----在dataObserver()方法里registerObserver().Observer(....)
 * 2.有页面的状态处理(Loading,Error,Success)如果需要重新定义则继承BaseViewStateControl重新定义页面的布局样式，然后setObserver()仿照默认的observer设定即可
 * 3.页面的状态管理交给LoadStateManager可插件化处理了，非常方便
 * 4.getStateEventKey()是事件线的key,要确保 @link BaseRepository中sendData的key保持一致
 */
public abstract class AbsLifecycleFragment<T extends AbsViewModel> extends BaseLazyRxFragment {
    protected T mViewModel;

    protected Object mStateEventKey;

    protected String mStateEventTag;

    private List<Object> eventKeys = new ArrayList<>();

    public void initViewModel() {
        mViewModel = VMProviders(this, (Class<T>) ClassUtil.getInstance(this, 0));
        if (null != mViewModel) {
            dataObserver();
            mStateEventKey = getStateEventKey();
            mStateEventTag = getStateEventTag();
        }
    }
    public void initViewModel(ParameterizedType type) {
        mViewModel = VMProviders(this, (Class<T>) ClassUtil.getInstance(type, 0));
        if (null != mViewModel) {
            dataObserver();
            mStateEventKey = getStateEventKey();
            mStateEventTag = getStateEventTag();
        }
    }
    /**
     * 页面状态的监听处理逻辑
     * eg:
     * 1.Repository获取数据后通过showPageState()方法返回状态码，
     * 2.observer响应结果，根据状态结果设置页面的显示(showLoading(),showSuccess(),showError())
     * */
    protected void setObserver(Observer observer){
        if (mViewModel!=null){
            eventKeys.add(new StringBuilder((String) mStateEventKey).append(mStateEventTag).toString());
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
            LiveBus.getDefault().subscribe(mStateEventKey, mStateEventTag).observe(this, observer);
        }
    }
    /**
     * ViewPager +fragment tag
     *
     * @return
     */
    protected String getStateEventTag() {
        return "";
    }

    /**
     * get state page event key
     *
     * @return
     */
    protected abstract Object getStateEventKey();

    /**
     * create ViewModelProviders
     *
     * @return ViewModel
     */
    protected <T extends ViewModel> T VMProviders(Fragment
                                                          fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (eventKeys != null && eventKeys.size() > 0) {
            for (int i = 0; i < eventKeys.size(); i++) {
                LiveBus.getDefault().clear(eventKeys.get(i));
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
