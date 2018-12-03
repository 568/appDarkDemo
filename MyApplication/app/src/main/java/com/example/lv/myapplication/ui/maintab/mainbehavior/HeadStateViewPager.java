package com.example.lv.myapplication.ui.maintab.mainbehavior;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lv on 18-11-30.
 */

public class HeadStateViewPager extends ViewPager {
    private MainBehaviorHelper mHelper;

    public HeadStateViewPager(Context context) {
        super(context);
        mHelper = MainBehaviorHelper.getInstance();
    }

    public HeadStateViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = MainBehaviorHelper.getInstance();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mHelper.isOpen()) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mHelper.isOpen()) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}
