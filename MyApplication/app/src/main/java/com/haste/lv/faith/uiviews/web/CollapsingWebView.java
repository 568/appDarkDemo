package com.haste.lv.faith.uiviews.web;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by lv on 19-4-28.
 * 解决AppBarLayout嵌套WebView滑动冲突的问题
 */

public class CollapsingWebView extends WebView {
    private GestureDetector detector;
    private boolean isScrollBottom = false;

    public CollapsingWebView(Context context) {
        this(context, null);
    }

    public CollapsingWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsingWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                if (!isScrollBottom) {
                    requestDisallowInterceptTouchEvent(true);
                } else {
                    isScrollBottom = false;
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float webcontent = getContentHeight() * getScale();
                float webnow = getHeight() + getScrollY();
                isScrollBottom = (Math.abs(webcontent - webnow) < 1);
                return true;
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
