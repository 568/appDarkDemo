package com.haste.lv.faith.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.haste.lv.faith.mvpvm.loadstatus.BaseViewStateControl;
import com.haste.lv.faith.mvpvm.loadstatus.LoadState;
import com.haste.lv.faith.mvpvm.loadstatus.LoadStateManager;
import com.haste.lv.faith.mvpvm.view.stateview.ErrorState;
import com.haste.lv.faith.mvpvm.view.stateview.LoadingState;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by lv on 18-11-30.
 * 懒加载
 * 使用说明：
 * 1.onFragmentFirstVisible页面的首次显示就会调用(首次包含了销毁重新创建)，这个方法会调用一次，可以用来做些初始化和savedInstanceState保存等
 * 2.设置是否使用 view 的复用，默认开启
 * 3.通过设置useLoadManager()确定是否使用状态页面，getContentResId()返回的是需要显示状态页面的第一个子view的id,这样会在它父视图位置添加新的FrameLayout
 * 4.关于状态页面的自定义可以继承BaseViewStateControl，自定义样式，然后调用mLoadStateManager.showStateView(...)方法即可显示
 * 5.默认有统一的ErrorState和LoadingState两种状态页面
 */
public abstract class BaseLazyRxFragment extends RxFragment {
    public abstract void loadData(long id);

    //
    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    protected View rootView;


    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    //如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (rootView == null) {
            rootView = view;
            if (useLoadManager()) {
                View contentLayout = rootView.findViewById(getContentResId());
                mLoadStateManager = new LoadStateManager.Builder()
                        .setViewParams(contentLayout == null ? rootView : contentLayout)
                        .setListener(new BaseViewStateControl.OnViewRefreshListener() {
                            @Override
                            public void onRefresh(View v) {
                                onStateRefresh();
                            }
                        })
                        .build(getStateBuilder());
            }
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {

    }

    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    /**
     * 加载页面状态的view资源的id
     */
    public int getContentResId() {
        return 0;
    }

    protected void onStateRefresh() {

    }

    /**
     * 是否使用LoadManager
     */
    protected boolean useLoadManager() {
        return false;
    }

    /*如果有新定义的状态页面，重写此方法进行注册*/
    protected LoadState.Builder getStateBuilder() {
        return LoadState.newInstance().getBuilder()
                .register(new LoadingState())
                .register(new ErrorState());
    }

    protected LoadStateManager mLoadStateManager;
}
