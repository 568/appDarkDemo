package com.haste.lv.faith.ui.maintab.mainbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.haste.lv.faith.R;

import java.util.List;

/**
 * Created by lv on 18-11-30.
 */

public class MainToolBarBehavior extends  MainBaseBehavior {
    private int mToolbarOffset;
    private int mToolbarHeight;
    private View mToolbar;

    private int mOffsetDelta;
    private View mContent;

    private View closeView;
    private MainBehaviorHelper mHelper;

    public MainToolBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mToolbarOffset = context.getResources().getDimensionPixelOffset(R.dimen.toobar_offset_height);
        mToolbarHeight = context.getResources().getDimensionPixelOffset(R.dimen.toobar_height);
        mHelper = MainBehaviorHelper.getInstance();
        //如果让toolbar一直显示那么设置mToolbarOffset=0;否则mToolbarOffset与mToolbarHeight值相反
        mToolbarOffset=0;
    }

    @Override
    protected boolean isOnDepend(View depend) {
        return depend != null && depend.getId() == R.id.main_head_pager;
    }

    @Override
    protected void initView(CoordinatorLayout parent, View child, View dependency) {
        mToolbar = child;
        closeView=mToolbar.findViewById(R.id.open_header_view);
        mContent = parent.findViewById(R.id.main_content_pager);
        mOffsetDelta = mHelper.getOffsetDelta();
        if (mOffsetDelta > 0) {
            sureInit = true;
        }
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (mHelper.isCanAsNeeded()) {
            offsetChildAsNeeded(parent, child, mContent);
        }
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {

        float translationY = dependency.getTranslationY();
        if (translationY >= 0.0f) {
//            child.setTranslationY(mToolbarOffset);
            closeView.setVisibility(View.GONE);
        } else if (Math.abs(translationY) >= mOffsetDelta) {
            //child.setTranslationY(mToolbarHeight);
            closeView.setVisibility(View.VISIBLE);
        } else {
            float v2 = translationY / mOffsetDelta;
//            child.setTranslationY(Math.abs(mToolbarHeight * v2));
            closeView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin = mToolbarOffset;
        parent.onLayoutChild(child, layoutDirection);
        return true;
    }


    @Override
    public void closeHeadPager(int Duration) {
       // mToolbar.animate().translationY(mToolbarHeight).setDuration(Duration);
        closeView.setVisibility(View.VISIBLE);
    }

    @Override
    public void openHeadPager(int Duration) {
        mToolbar.animate().translationY(mToolbarOffset).setDuration(Duration);
        //关闭回顶部按钮
        closeView.setVisibility(View.GONE);
    }

    @Override
    public View findFirstDependency(List<View> views) {
        if (views != null) {
            for (View v : views) {
                if (isOnDepend(v)) {
                    return v;
                }
            }
        }
        return null;
    }
}
