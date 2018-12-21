package com.haste.lv.faith.ui.maintab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;
import com.haste.lv.faith.adapters.BaseFragmentPagerAdapter;
import com.haste.lv.faith.ui.BaseLazyRxFragment;
import com.haste.lv.faith.ui.maintab.childs.MainChildFragment;
import com.haste.lv.faith.ui.maintab.childs.TabLayoutStyleFragment;
import com.haste.lv.faith.ui.maintab.childs.TabLoadingStyleFragment;
import com.haste.lv.faith.ui.maintab.mainbehavior.HeadStateViewPager;
import com.haste.lv.faith.ui.maintab.mainbehavior.MainBehaviorHelper;
import com.haste.lv.faith.uiviews.tablayout.SlidingTabLayout;
import com.haste.lv.faith.uiviews.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-11-30.
 */

public class TabMainFragment extends BaseLazyRxFragment implements MainBehaviorHelper.onHeadstateListener, OnTabSelectListener {
    SlidingTabLayout mTab;
    HeadStateViewPager mViewpager;

    private List<BaseLazyRxFragment> mFragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MainBehaviorHelper.initInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab_main_layout, container, false);
        View mToolbar = view.findViewById(R.id.open_header_view);
        mTab = view.findViewById(R.id.main_tab_pager);
        mViewpager = view.findViewById(R.id.main_content_pager);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainBehaviorHelper.getInstance().isOpen()) {
                } else {
                    MainBehaviorHelper.getInstance().openHeadPager();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        mFragments = new ArrayList<>();
        String[] titles = {"关注", "推荐", "热点", "新时代", "体育", "财经频道", "好购物", "汽车", "LoadingStyle", "tabLayout样式"};
        for (int j = 0; j < 8; j++) {
            mFragments.add(new MainChildFragment());
        }
        mFragments.add(new TabLoadingStyleFragment());
        mFragments.add(new TabLayoutStyleFragment());

        mViewpager.setOffscreenPageLimit(10);
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mFragments, titles);
        mViewpager.setAdapter(adapter);

        mTab.setViewPager(mViewpager);
        mTab.setOnTabSelectListener(this);

        mTab.showDot(1);
        mTab.showMsg(2, 11);
        mTab.setMsgMargin(2, 0, 10);
        MainBehaviorHelper.getInstance().setListener(this);
        /**
         *       设置上推后，不能下拉
         */
        //
        MainBehaviorHelper.getInstance().setCloseAfterEndabled(false);
    }

    @Override
    public void loadData(long id) {

    }

    @Override
    public void onHeadIsOpen(boolean state) {
        for (BaseLazyRxFragment u : mFragments) {
            if (u instanceof MainChildFragment) {
                ((MainChildFragment) u).setHeadState(!state);
            }else if (u instanceof TabLayoutStyleFragment){
                ((TabLayoutStyleFragment) u).setHeadState(!state);
            }
        }
    }

    @Override
    public void onTabSelect(int position) {
        if (position == 1) {
            mTab.hideMsg(1);
        } else if (position == 2) {
            mTab.hideMsg(2);
        }
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected boolean useLoadManager() {
        return false;
    }
}
