package com.haste.lv.faith.ui.maintab;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.haste.lv.faith.FirstActivity;
import com.haste.lv.faith.R;
import com.haste.lv.faith.mvpvm.base.BaseAbsLifecycleFragment;
import com.haste.lv.faith.ui.BaseLazyRxFragment;
import com.haste.lv.faith.ui.maintab.adapter.TabMoreAdapter;
import com.haste.lv.faith.ui.maintab.bean.SettingHeaderItem;
import com.haste.lv.faith.ui.maintab.bean.SettingItem;
import com.haste.lv.faith.ui.maintab.childs.AboutFragment;
import com.haste.lv.faith.ui.maintab.viewmodel.SettingViewModel;
import com.haste.lv.faith.ui.maintab.views.PullZoomRecyclerView;
import com.haste.lv.faith.uiviews.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.haste.lv.faith.uiviews.recyclerview.divider.XHorizontalDividerItemDecoration;
import com.haste.lv.faith.utils.SharedPreferencesHelper;

import java.util.ArrayList;

/**
 * Created by lv on 18-11-30.
 * 更多tab
 */

public class TabMoreFragment extends BaseAbsLifecycleFragment implements View.OnClickListener {
    private PullZoomRecyclerView refreshRecyclerView;
    private SettingViewModel mSettingViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab_my_layout, container, false);
        refreshRecyclerView = view.findViewById(R.id.recyclerview_content);
        return view;
    }

    @Override
    public void loadData(long id) {

    }

    TabMoreAdapter mTabMoreAdapter;

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        //观察者注册
        mSettingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        // 订阅LiveData中当前SettingItem数据变化
        mSettingViewModel.getCurrentSettingData().observe(this, new Observer<SettingItem>() {
            @Override
            public void onChanged(@Nullable SettingItem settingItem) {
                if (settingItem.isChecked) {
                    if (!SharedPreferencesHelper.getBoolean("APP_NIGHT_MODE", false)) {
                        SharedPreferencesHelper.applyBoolean("APP_NIGHT_MODE", true);
                        nightView = changeActNightMode(getActivity());
                    }
                } else {
                    if (SharedPreferencesHelper.getBoolean("APP_NIGHT_MODE", false)) {
                        SharedPreferencesHelper.applyBoolean("APP_NIGHT_MODE", false);
                        removeActNightMode(getActivity(), nightView);
                    }
                }
            }
        });
        //列表适配器设定
        ArrayList<SettingItem> dataList = new ArrayList<>();
        mTabMoreAdapter = new TabMoreAdapter(dataList, getContext());
        mTabMoreAdapter.init(refreshRecyclerView, mSettingViewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRecyclerView.setAdapter(mTabMoreAdapter);
        refreshRecyclerView.setLayoutManagerAndDivider(layoutManager, new XHorizontalDividerItemDecoration.Builder(getContext()).color(0xffeaeaea).startSkipCount(1).build());
        mTabMoreAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<SettingItem>() {
            @Override
            public void onItemClick(View view, SettingItem item, int position) {
                if (position == 1) {
                    Intent intent = new Intent(getContext(), FirstActivity.class);
                    startActivity(intent);
                } else if (position == 3) {
                    startContainerActivity(AboutFragment.class.getCanonicalName());
                }
            }
        });
        //页面列表数据
        SettingItem header = new SettingHeaderItem();
        header.itemType = 100;
        SettingItem item1 = new SettingItem();
        item1.itemType = 1;
        item1.title = "夜间模式";
        SettingItem item2 = new SettingItem();
        item2.itemType = 2;
        item2.title = "账号信息";

        SettingItem item3 = new SettingItem();
        item3.itemType = 2;
        item3.title = "关于我们";

        dataList.add(header);
        dataList.add(item2);
        dataList.add(item1);
        dataList.add(item3);
    }

    @Override
    public void onClick(View view) {
    }

    View nightView;

    private View changeActNightMode(Activity activity) {
        WindowManager mWindowManager = activity.getWindowManager();//(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams nightViewParam = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                PixelFormat.TRANSPARENT);
        nightViewParam.width = ViewGroup.LayoutParams.MATCH_PARENT;
        nightViewParam.height = ViewGroup.LayoutParams.MATCH_PARENT;
        nightViewParam.gravity = Gravity.CENTER;
        View nightView = new View(getContext());
        nightView.setBackgroundColor(0x66000000);
        mWindowManager.addView(nightView, nightViewParam);
        return nightView;

    }

    private void removeActNightMode(Activity activity, View view) {
        if (null == view)
            return;
        WindowManager mWindowManager = activity.getWindowManager();// (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.removeViewImmediate(view);
    }

    @Override
    protected boolean useLoadManager() {
        return false;
    }
}
