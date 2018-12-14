package com.haste.lv.faith.ui.maintab.childs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;
import com.haste.lv.faith.ui.BaseLazyRxFragment;
import com.haste.lv.faith.uiviews.tablayout.SegmentTabLayout;
import com.haste.lv.faith.uiviews.tablayout.SlidingTabLayout;
import com.haste.lv.faith.uiviews.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * Created by lv on 18-12-4.
 * 展示tablayout样式
 * 注：1.页面的设计基点，仅仅是为了展示而已
 */

public class TabLayoutStyleFragment extends BaseLazyRxFragment {
    private String[] mTitles = {"首页", "消息"};
    private String[] mTitles_2 = {"首页", "消息", "联系人"};
    private String[] mTitles_3 = {"首页", "消息", "联系人", "更多"};

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    private NestedScrollView mNestedScrollView;
    private SegmentTabLayout mTabLayout1,mTabLayout2,mTabLayout3,mTabLayout4;
    private SlidingTabLayout mSTabLayout1,mSTabLayout2,mSTabLayout3,mSTabLayout4,mSTabLayout5,mSTabLayout6,mSTabLayout7;
    private ViewPager mViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maintab_tabstyle_layout, container, false);
        mNestedScrollView=rootView.findViewById(R.id.mNestedScrollView);
        //
        mTabLayout1=rootView.findViewById(R.id.tl_1);
        mTabLayout2=rootView.findViewById(R.id.tl_2);
        mTabLayout3=rootView.findViewById(R.id.tl_3);
        mTabLayout4=rootView.findViewById(R.id.tl_4);
        mViewPager=rootView.findViewById(R.id.vp_2);
        //
        mSTabLayout1=rootView.findViewById(R.id.tl_42);
        mSTabLayout2=rootView.findViewById(R.id.tl_52);
        mSTabLayout3=rootView.findViewById(R.id.tl_62);
        mSTabLayout4=rootView.findViewById(R.id.tl_72);
        mSTabLayout5=rootView.findViewById(R.id.tl_82);
        mSTabLayout6=rootView.findViewById(R.id.tl_92);
        mSTabLayout7=rootView.findViewById(R.id.tl_102);
        //
        return rootView;
    }

    @Override
    protected void onFragmentFirstVisible() {

        for (String title : mTitles_3) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
        }

        for (String title : mTitles_2) {
            mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));
        }

        mTabLayout1.setTabData(mTitles);
        mTabLayout2.setTabData(mTitles_2);
        mTabLayout4.setTabData(mTitles_2);
        //显示未读红点
        mTabLayout1.showDot(2);
        mTabLayout2.showDot(2);
        mTabLayout4.showDot(1);

        //与viewpager关联demo
        setUpViewPager();
        mTabLayout3.showDot(1);
        //
    }

    private void setUpViewPager(){
        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));

        mTabLayout3.setTabData(mTitles_3);
        mTabLayout3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSTabLayout1.setViewPager(mViewPager);
        mSTabLayout2.setViewPager(mViewPager);
        mSTabLayout3.setViewPager(mViewPager);
        mSTabLayout4.setViewPager(mViewPager);
        mSTabLayout5.setViewPager(mViewPager);
        mSTabLayout6.setViewPager(mViewPager);
        mSTabLayout7.setViewPager(mViewPager);
    }
    @Override
    public void loadData(long id) {

    }
    public void setHeadState(boolean canScroll) {
        if (!canScroll) {
            if (mNestedScrollView != null) {
                mNestedScrollView.scrollTo(0,0);
            }
        }
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_3[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
