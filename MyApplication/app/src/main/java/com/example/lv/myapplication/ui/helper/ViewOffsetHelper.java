package com.example.lv.myapplication.ui.helper;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by lv on 18-11-30.
 * <p>
 * 使用ViewOffsetHelper工具类封装View的偏移量。
 * View类支持对offset进行偏移，但是并不会保存偏移量。
 * ViewOffsetHelper对Offset和Top/Left进行缓存，使用ViewCompat工具类进行偏移处理。
 */

public class ViewOffsetHelper {
    private final View mView;

    private int mLayoutTop;
    private int mLayoutLeft;
    private int mOffsetTop;
    private int mOffsetLeft;

    public ViewOffsetHelper(View view) {
        mView = view;
    }

    public void onViewLayout() {
        // Now grab the intended top
        mLayoutTop = mView.getTop();
        mLayoutLeft = mView.getLeft();

        // And offset it as needed
        updateOffsets();
    }

    private void updateOffsets() {
        ViewCompat.offsetTopAndBottom(mView, mOffsetTop - (mView.getTop() - mLayoutTop));
        ViewCompat.offsetLeftAndRight(mView, mOffsetLeft - (mView.getLeft() - mLayoutLeft));
    }

    /**
     * Set the top and bottom offset for this {@link ViewOffsetHelper}'s view.
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    public boolean setTopAndBottomOffset(int offset) {
        if (mOffsetTop != offset) {
            mOffsetTop = offset;
            updateOffsets();
            return true;
        }
        return false;
    }

    /**
     * Set the left and right offset for this {@link ViewOffsetHelper}'s view.
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    public boolean setLeftAndRightOffset(int offset) {
        if (mOffsetLeft != offset) {
            mOffsetLeft = offset;
            updateOffsets();
            return true;
        }
        return false;
    }

    public int getTopAndBottomOffset() {
        return mOffsetTop;
    }

    public int getLeftAndRightOffset() {
        return mOffsetLeft;
    }

    public int getLayoutTop() {
        return mLayoutTop;
    }

    public int getLayoutLeft() {
        return mLayoutLeft;
    }
}
