package com.haste.lv.faith.ui.maintab.mainbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.haste.lv.faith.R;

import java.util.List;

/**
 * Created by lv on 18-11-30.
 * 主页导航条的事件消费处理
 * 1.处理导航条的显示或隐藏，依据头部区域的可滑动范围处理
 */

public class MainTabBehavior extends MainBaseBehavior {
    private int mTabHeight;
    private View mTab;
    private int mTabTop;
    private int mToolbarHeight;
    private View mContent;

    private int mOffsetDelta;

    private MainBehaviorHelper mHelper;

    public MainTabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = MainBehaviorHelper.getInstance();
        mToolbarHeight = context.getResources().getDimensionPixelOffset(R.dimen.toobar_height);
    }

    @Override
    protected boolean isOnDepend(View depend) {
        return depend != null && depend.getId() == R.id.main_head_pager;
    }

    @Override
    protected void initView(CoordinatorLayout parent, View child, View dependency) {
        mTab = child;
        mTabHeight = child.getMeasuredHeight();
        mTabTop = child.getTop();
        mContent = parent.findViewById(R.id.main_content_pager);
        /**
         * 计算可以滑动高度
         */
        mOffsetDelta = mHelper.getOffsetDelta();
        if (mOffsetDelta > 0) {
            sureInit = true;
        }
        //  mOffsetDelta = dependency.getMeasuredHeight() - mToolbarHeight - mTabHeight;
        //dependency:+id/main_head_pager;child:+id/main_tab_pager
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = dependency.getTranslationY();
        Log.e("tag_mtcb", "tab onDependentViewChanged translationY = " + translationY +" " +mHelper.isCanAsNeeded());
        if (mHelper.isCanAsNeeded()) {
            offsetChildAsNeeded(parent, child, mContent);
        }else{
        }
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
        float translationY = dependency.getTranslationY();
        float offsetRange = dependency.getTop() + mToolbarHeight - child.getTop();
        Log.e("tag_mtcb", "tab translationY = " + translationY );
        Log.e("tag_mtb","mTabTop= " +mTabTop +" mTabHeight = "+mTabHeight+" offsetRange = " +offsetRange +" translationY = "+translationY );
        if (translationY >= 0.0f) {
            child.setTranslationY(mTabTop);
        } else if (Math.abs(translationY) >= mOffsetDelta) {
            child.setTranslationY(translationY - mTabHeight);
        } else {
            float v1 = translationY / mOffsetDelta;
            float v = translationY + (mTabHeight * v1);
            child.setTranslationY(v);
            Log.e("tag_mtb","v1= " +v1 +" v = " +v);
        }
        Log.e("tag_mtb","translationY = "+translationY +" mOffsetDelta = " +mOffsetDelta +" ");
    }


    @Override
    public void openHeadPager(int Duration) {

        mTab.animate().translationY(mTabTop).setDuration(Duration);
    }

    @Override
    public void closeHeadPager(int Duration) {
        Log.e("tag_mtb","mOffsetDelta = " +mOffsetDelta +" mTabHeight = "+mTabHeight +"  -Y = "+(mOffsetDelta + mTabHeight));
        mTab.animate().translationY(-(mOffsetDelta + mTabHeight)).setDuration(Duration);
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
