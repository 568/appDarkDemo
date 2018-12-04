package com.haste.lv.faith.ui.maintab.mainbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.haste.lv.faith.R;

import java.util.List;

/**
 * Created by lv on 18-11-30.
 * 主页滑动事件消费处理
 * 1.这个处理内容区域与头部的关系，即viewpager与headview区域关系，同时处理内容与导航条区域的跟随滑动显示或隐藏
 * 2.在头部加了时差缩放显示效果.
 */

public class MainContentBehavior extends MainBaseBehavior {
    /**
     * 可移动总高度
     */
    private int mOffsetDelta;
    /**
     * 头部view高度
     */
    private int mHeadPageHeight;
    /**
     * Toolbar高度
     */
    private int mToolbarHeight;
    /**
     * Tablayout高度
     */
    private int mTabLayoutHeight;

    /**
     * 依赖view,即 R.id.main_head_pager的view
     */
    private View mDependencyView;

    private View mContnetView;

    private View mTabView;
    private MainBehaviorHelper mHelper;


    public MainContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = MainBehaviorHelper.getInstance();
    }

    @Override
    protected boolean isOnDepend(View depend) {
        return depend != null && depend.getId() == R.id.main_head_pager;
    }

    @Override
    protected void initView(CoordinatorLayout parent, View child, View dependency) {
        mDependencyView = dependency;
        mContnetView = child;
        mTabView = parent.findViewById(R.id.main_tab_pager);
        mHeadPageHeight = dependency.getMeasuredHeight();
        mToolbarHeight = parent.findViewById(R.id.main_toolbar_pager).getMeasuredHeight();
        mTabLayoutHeight = parent.findViewById(R.id.main_tab_pager).getMeasuredHeight();
        /**
         * 计算可以滑动高度
         */
        mOffsetDelta = mHeadPageHeight - mToolbarHeight - mTabLayoutHeight;
        /**
         * 保存滑动高度
         */
        mHelper.setOffsetDelta(mOffsetDelta);
        /**
         * 这是一个大bug，若不设置，(mToolbarHeight + mTabLayoutHeight)高度的内容给遮挡不会显示
         */
        if ((mToolbarHeight + mTabLayoutHeight) > 0) {
            child.setPadding(child.getPaddingLeft(), child.getPaddingRight(), child.getPaddingTop(), mToolbarHeight + mTabLayoutHeight);
            sureInit = true;
        }

    }


    /**
     * 计算是否可以滑动
     *
     * @param child
     * @param pendingDy
     * @return
     */
    private boolean canScroll(View child, float pendingDy) {
        int pendingTranslationY = (int) (child.getTranslationY() - pendingDy);
        if (pendingTranslationY >= getOffsetRange() && (pendingTranslationY <= 0)) {
            return true;
        }
        return false;
    }

    private float lastVelocityY = 0;

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        // consumed the flinging behavior until Closed
        //监听快速滑动，是否要消费事件，若不判断recyclerview会快速滑动
        lastVelocityY = velocityY;
        return !isClosed(child);
    }


    private boolean isClosed(View child) {
        return child.getTranslationY() == getOffsetRange();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (ViewCompat.SCROLL_AXIS_VERTICAL & nestedScrollAxes) != 0 && canScroll(child, 0) && isOnScrollTop(child, target) && mHelper.isCloseAfterEndabled();
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);


        float translationY = child.getTranslationY();
        /**
         * 计算缩放比,即折叠率
         */
        float i = translationY / getOffsetRange() / 5;
        Log.e("tag_mcb", "onNestedPreScroll 折叠率:" + i + " translationY = " + translationY + " mOffsetDelta:" + mOffsetDelta + " dy:" + dy);
        Log.e("tag_mtcb", "content translationY = " + translationY);
        //消费掉其中的4分之1，不至于滑动效果太灵敏
        float halfOfDis = dy / 4.0f;

        //是否增加抖动
        boolean shake = true;
        if (shake) {
            Log.e("tag_mtcb", "content +++++++++translationY = " + translationY + " mOffsetDelta:" + mOffsetDelta + " dy:" + dy +"  "+Math.abs(dy) / 3);
            if (!canScroll(child, halfOfDis)) {
                Log.e("tag_mtcb", "halfOfDis = " +halfOfDis );
                child.setTranslationY(halfOfDis > 0 ? getOffsetRange() : 0);
            } else if (Math.abs(dy) / 3 > 5) { //去抖动
                if (translationY - halfOfDis <= 0) {
                    child.setTranslationY(translationY - halfOfDis);
                    mDependencyView.setTranslationY((translationY * i));
                }
            }
            //当translationY与mOffsetDelta相等的时候，滑动时候在translationY不发生变化的情况下 onDependentViewChanged事件是不会响应的
            //此时mTabView在顶部，此时把mTabView完全展示即可，
            //如果headerview完全展示即open的时候，滑动时候在translationY不发生变化的情况下 onDependentViewChanged事件是不会响应的
            //此时mTabView在底部，此时把mTabView完全隐藏即可
            if (Math.abs(translationY) >= mOffsetDelta) {
                mTabView.setTranslationY(translationY-mTabLayoutHeight);
            }else if (Math.abs(translationY) ==0){
                mTabView.setTranslationY(0);
            }
        } else {
            if (!canScroll(child, halfOfDis)) {
                if (halfOfDis > 0) {
                    child.setTranslationY(getOffsetRange());
                } else {
                    child.setTranslationY(0);
                }
            } else {
                //滑动未结束
                //滑动
                child.setTranslationY(translationY - halfOfDis);
            }
        }
        consumed[1] = dy;

    }


    /**
     * 判断是否recyclerview，若是recyclerview，则判断是否已经滑动到第一个item
     * to judge whether current recyclerview'item is at the top of recyclerview
     *
     * @param child
     * @param target
     * @return
     */
    private boolean isOnScrollTop(View child, View target) {
        if (target instanceof RecyclerView) {
            boolean isRVTop = ((LinearLayoutManager) ((RecyclerView) target)
                    .getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0;
            return isRVTop;
        }
        return true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);

        final float translationY = child.getTranslationY();

        Log.e("tag_mcb", "onStopNestedScroll translationY = " + translationY + " mOffsetDelta:" + mOffsetDelta);

        if (translationY >= 0) {
            //设置打开状态
            mHelper.setOpenState();
        } else if (Math.abs(translationY) >= mOffsetDelta) {
            //设置关闭状态
            mHelper.setCloseState();
        }
        float v = (mOffsetDelta + translationY) / mOffsetDelta;
        //去抖动
        //在这里如果不加上v==0判断当推上去的时候tabLayout导航会卡在一定位置上不去，但是加上后在反复滑动时候出现导航条与内容页跟随缓慢现象
        //这里判断v==0表示一定是滑动到顶端了，必须保证导航条的位置显示正确
        if (v > 0.001f && v < 0.5f || lastVelocityY >= 4000 || v == 0) {//当滑动比小于x时,则显示toolbar和快速滑动值
            mHelper.closeHeadPager();
        } else if (v >= 0.5f || lastVelocityY <= -4000) {//当滑动比大于x时,则关闭toolbar
            mHelper.openHeadPager();
        }
        lastVelocityY = 0;
    }


    @Override
    public void openHeadPager(int Duration) {
        ViewCompat.animate(mContnetView).translationY(0).setDuration(Duration)
                .setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(View view) {
                        float translationY = view.getTranslationY();
                        /**
                         * 计算缩放比,即折叠率
                         */
                        float i = translationY / getOffsetRange() / 5;
                        mDependencyView.setTranslationY(translationY * i);
                    }
                });
    }

    @Override
    public void closeHeadPager(int Duration) {
        mContnetView.animate().translationY(-mOffsetDelta).setDuration(Duration);
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


    public int getOffsetRange() {
        return -mOffsetDelta;
    }
}
