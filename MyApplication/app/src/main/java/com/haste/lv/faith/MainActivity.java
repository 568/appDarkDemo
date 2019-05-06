package com.haste.lv.faith;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.haste.lv.faith.adapters.BaseFragmentPagerAdapter;
import com.haste.lv.faith.core.HFBaseActivity;
import com.haste.lv.faith.ui.BaseLazyRxFragment;
import com.haste.lv.faith.ui.maintab.TabDiscoveryFragment;
import com.haste.lv.faith.ui.maintab.TabMainFragment;
import com.haste.lv.faith.ui.maintab.TabMoreFragment;
import com.haste.lv.faith.ui.maintab.TabScheduleFragment;
import com.haste.lv.faith.ui.maintab.TabVideoFragment;
import com.haste.lv.faith.ui.maintab.childs.TabEntity;
import com.haste.lv.faith.uiviews.tablayout.CommonTabLayout;
import com.haste.lv.faith.uiviews.tablayout.listener.CustomTabEntity;
import com.haste.lv.faith.uiviews.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends HFBaseActivity {

    TabVideoFragment mTabVideoFragment;
    TabScheduleFragment mTabScheduleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setupViewPager(viewPager);
    }

    private void setupViewPager(final ViewPager viewPager) {
        String[] titles = {"主页", "视频", "发现", "日程", "更多"};
        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        bottomNavigationView.setTabData(mTabEntities);
        bottomNavigationView.showDot(3);
        bottomNavigationView.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.setCurrentTab(position);
                switch (position) {
                    case 1:
                        mTabVideoFragment.initImmersionBar();
                        break;
                    case 2:
                        break;
                    case 3:
                        mTabScheduleFragment.initImmersionBar();
                        break;
                    case 4:
                        break;
                    case 0:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        List<BaseLazyRxFragment> fragments = new ArrayList<>();
        fragments.add(new TabMainFragment());
        fragments.add((mTabVideoFragment = new TabVideoFragment()));
        fragments.add(new TabDiscoveryFragment());
        fragments.add(mTabScheduleFragment = new TabScheduleFragment());
        fragments.add(new TabMoreFragment());
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
    }

    private ViewPager viewPager;
    private CommonTabLayout bottomNavigationView;
    private int[] mIconUnselectIds = {
            R.drawable.teb_home_off, R.drawable.teb_explore_off,
            R.drawable.teb_explore_off, R.drawable.teb_explore_off, R.drawable.teb_me_off};
    private int[] mIconSelectIds = {
            R.drawable.teb_home_on, R.drawable.teb_explore_on,
            R.drawable.teb_explore_on, R.drawable.teb_explore_on, R.drawable.teb_me_on};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public void onBackPressed() {
        if (mTabVideoFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
