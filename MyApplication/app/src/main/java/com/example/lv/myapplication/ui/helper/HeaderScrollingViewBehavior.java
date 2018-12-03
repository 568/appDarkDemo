package com.example.lv.myapplication.ui.helper;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lv on 18-11-30.
 * 正常来说被依赖的 View 会优先于依赖它的 View 处理，所以需要依赖的 View 可以在 measure/layout 的时候，
 * 找到依赖的 View 并获取到它的测量/布局的信息，这里的处理就是依靠着这种关系来实现的
 * <p>
 * 职责主要是完成对ScrollingView的布局。CoL的职责是给子类提供协调滚动的接口，并不会具体实现某种效果，
 * 所有子类需要完成的功能和效果，都需要通过统一接口Behavior完成.
 * <p>
 * 在Header+ScrollingView的结构中，HeaderBehavior完成对Touch事件的处理，而HeaderScrollingViewBehavior要完成的，
 * 就是对ScrollingView的控制。这两者结合要实现的就是MaterialDesign中经典的可收起Header的效果。
 * <p>
 * 为了让Header可收起，视觉上ScrollingView的高度被拉长了，但实际上ScrollingView的高度并没有变，变的是ScrollingView的位置。
 * ScrollingView的测量和布局工作就是HeaderScrollingViewBehavior的实现内容。
 */

public abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior<View> {
    final Rect mTempRect1 = new Rect();
    final Rect mTempRect2 = new Rect();

    private int mVerticalLayoutGap = 0;
    private int mOverlayTop;

    public HeaderScrollingViewBehavior() {
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 只要child的LayoutParams是MATCH_PARENT或者WRAP_CONTENT，就设置child的高度为最大可见高度。
     * 这里的最大可见高度包含除header之外的区域以及header收起时额外空出的区域，也就是header的可滚动区域。
     * 1.onLayout中将ScrollingView置于header下方。
     * 2.这里Rect的top值取header.getBottom() + lp.topMargin，而不是getPaddingTop() + header.getHeight() + lp.topMargin，
     * 这是因为header在onLayout时可能已经包含偏移量，不能假定header在初始位置，即便可能90%的情况均是如此。
     */
    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child,
                                  int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec,
                                  int heightUsed) {
        final int childLpHeight = child.getLayoutParams().height;
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
                || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            // If the menu's height is set to match_parent/wrap_content then measure it
            // with the maximum visible height

            final List<View> dependencies = parent.getDependencies(child);
            final View header = findFirstDependency(dependencies);
            if (header != null) {
                if (ViewCompat.getFitsSystemWindows(header)
                        && !ViewCompat.getFitsSystemWindows(child)) {
                    // If the header is fitting system windows then we need to also,
                    // otherwise we'll get CoL's compatible measuring
                    ViewCompat.setFitsSystemWindows(child, true);

                    if (ViewCompat.getFitsSystemWindows(child)) {
                        // If the set succeeded, trigger a new layout and return true
                        child.requestLayout();
                        return true;
                    }
                }

                int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
                if (availableHeight == 0) {
                    // If the measure spec doesn't specify a size, use the current height
                    availableHeight = parent.getHeight();
                }

                final int height = availableHeight - header.getMeasuredHeight()
                        + getScrollRange(header);
                final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
                        childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
                                ? View.MeasureSpec.EXACTLY
                                : View.MeasureSpec.AT_MOST);

                // Now measure the scrolling view with the correct height
                parent.onMeasureChild(child, parentWidthMeasureSpec,
                        widthUsed, heightMeasureSpec, heightUsed);

                return true;
            }
        }
        return false;
    }

    @Override
    protected void layoutChild(final CoordinatorLayout parent, final View child,
                               final int layoutDirection) {
        final List<View> dependencies = parent.getDependencies(child);
        final View header = findFirstDependency(dependencies);

        if (header != null) {
            final CoordinatorLayout.LayoutParams lp =
                    (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            final Rect available = mTempRect1;
            available.set(parent.getPaddingLeft() + lp.leftMargin,
                    header.getBottom() + lp.topMargin,
                    parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
                    parent.getHeight() + header.getBottom()
                            - parent.getPaddingBottom() - lp.bottomMargin);

            final Rect out = mTempRect2;
            GravityCompat.apply(resolveGravity(lp.gravity), child.getMeasuredWidth(),
                    child.getMeasuredHeight(), available, out, layoutDirection);

            final int overlap = getOverlapPixelsForOffset(header);

            child.layout(out.left, out.top - overlap, out.right, out.bottom - overlap);
            mVerticalLayoutGap = out.top - header.getBottom();
        } else {
            // If we don't have a dependency, let super handle it
            super.layoutChild(parent, child, layoutDirection);
            mVerticalLayoutGap = 0;
        }
    }

    float getOverlapRatioForOffset(final View header) {
        return 1f;
    }

    final int getOverlapPixelsForOffset(final View header) {
        return mOverlayTop == 0 ? 0 : constrain(
                (int) (getOverlapRatioForOffset(header) * mOverlayTop), 0, mOverlayTop);
    }

    private static int resolveGravity(int gravity) {
        return gravity == Gravity.NO_GRAVITY ? GravityCompat.START | Gravity.TOP : gravity;
    }

    public abstract View findFirstDependency(List<View> views);

    public int getScrollRange(View v) {
        return v.getMeasuredHeight();
    }

    /**
     * The gap between the top of the scrolling view and the bottom of the header layout in pixels.
     */
    final int getVerticalLayoutGap() {
        return mVerticalLayoutGap;
    }

    /**
     * Set the distance that this view should overlap any
     *
     * @param overlayTop the distance in px
     */
    public final void setOverlayTop(int overlayTop) {
        mOverlayTop = overlayTop;
    }

    /**
     * Returns the distance that this view should overlap any
     */
    public final int getOverlayTop() {
        return mOverlayTop;
    }


    public int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    public float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }


}
