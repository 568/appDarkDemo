package com.example.lv.myapplication.ui.maintab.mainbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.example.lv.myapplication.ui.helper.HeaderScrollingViewBehavior;

/**
 * Created by lv on 18-11-30.
 */

public abstract class MainBaseBehavior extends HeaderScrollingViewBehavior {
    protected Context mContext;

    protected boolean sureInit = false;

    public MainBaseBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        MainBehaviorHelper.getInstance().put(this);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        boolean onDepend = isOnDepend(dependency);
        if (onDepend && !sureInit) {
            initView(parent, child, dependency);
        }
        return onDepend;
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {


        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
        sureInit = false;
        MainBehaviorHelper.getInstance().delete(this);
    }


    /**
     * 判断依赖view
     *
     * @param depend
     * @return
     */
    protected abstract boolean isOnDepend(View depend);


    /**
     * 这个方法会在创建时，多次调用的
     *
     * @param parent
     * @param child
     * @param dependency
     */
    protected abstract void initView(CoordinatorLayout parent, View child, View dependency);


    /**
     * 打开头部页面
     */
    public abstract void openHeadPager(int Duration);

    /**
     * 关闭头部页面
     */
    public abstract void closeHeadPager(int Duration);
}
