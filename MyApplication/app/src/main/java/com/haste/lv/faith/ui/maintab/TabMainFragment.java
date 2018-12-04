package com.haste.lv.faith.ui.maintab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;
import com.haste.lv.faith.adapters.BaseFragmentPagerAdapter;
import com.haste.lv.faith.ui.BaseLazyFragment;
import com.haste.lv.faith.ui.maintab.childs.MainChildFragment;
import com.haste.lv.faith.ui.maintab.mainbehavior.HeadStateViewPager;
import com.haste.lv.faith.ui.maintab.mainbehavior.MainBehaviorHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-11-30.
 */

public class TabMainFragment extends BaseLazyFragment implements MainBehaviorHelper.onHeadstateListener {
    TabLayout mTab;
    HeadStateViewPager mViewpager;

    private List<BaseLazyFragment> mFragments;

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
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        mFragments = new ArrayList<>();
        String[] titles = new String[10];
        for (int j = 0; j < 10; j++) {
            titles[j] = ("tab" + j);
            mFragments.add(new MainChildFragment());
        }
        mViewpager.setOffscreenPageLimit(10);
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mFragments, titles);
        mViewpager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewpager);

        MainBehaviorHelper.getInstance().setListener(this);
        /**
         *       设置上推后，不能下拉
         */

        MainBehaviorHelper.getInstance().setCloseAfterEndabled(false);
    }

    @Override
    public void loadData(long id) {

    }

    @Override
    public void onHeadIsOpen(boolean state) {
        for (BaseLazyFragment u : mFragments) {
            ((MainChildFragment) u).setHeadState(!state);
        }
    }
}
