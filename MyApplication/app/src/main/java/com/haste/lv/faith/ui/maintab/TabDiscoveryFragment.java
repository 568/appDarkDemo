package com.haste.lv.faith.ui.maintab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haste.lv.faith.R;
import com.haste.lv.faith.ui.BaseLazyRxFragment;
import com.haste.lv.faith.ui.maintab.adapter.TabDSCardAdapter;
import com.haste.lv.faith.ui.maintab.discovery.PagerChangeListener;

/**
 * Created by lv on 18-11-30.
 * 第三个tab
 */

public class TabDiscoveryFragment extends BaseLazyRxFragment {
    ViewPager mVpContainer;
    TabLayout mTlTab;
    ImageView mIvOutgoing;
    ImageView mIvTarget;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab_discovery_layout, container, false);
        mVpContainer = view.findViewById(R.id.main_vp_container);
        mTlTab = view.findViewById(R.id.toolbar_tl_tab);
        mIvOutgoing = view.findViewById(R.id.appbar_iv_outgoing);
        mIvTarget = view.findViewById(R.id.appbar_iv_target);
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        // 设置ViewPager布局
        TabDSCardAdapter adapter = new TabDSCardAdapter(getActivity().getSupportFragmentManager());
        mVpContainer.setAdapter(adapter);
        mVpContainer.addOnPageChangeListener(PagerChangeListener.newInstance(adapter, mIvTarget, mIvOutgoing));
        mTlTab.setupWithViewPager(mVpContainer); // 注意在Toolbar中关联ViewPager
    }

    @Override
    public void loadData(long id) {

    }

    @Override
    protected boolean useLoadManager() {
        return false;
    }

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }
}

