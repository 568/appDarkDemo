package com.haste.lv.faith.ui.maintab;

import android.app.Activity;
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
import com.haste.lv.faith.ui.BaseLazyFragment;
import com.haste.lv.faith.ui.maintab.adapter.TabMoreAdapter;
import com.haste.lv.faith.ui.maintab.bean.SettingHeaderItem;
import com.haste.lv.faith.ui.maintab.bean.SettingItem;
import com.haste.lv.faith.ui.maintab.views.PullZoomRecyclerView;
import com.haste.lv.faith.uiviews.recyclerview.PullToRefreshRecyclerView;
import com.haste.lv.faith.utils.SharedPreferencesHelper;

import java.util.ArrayList;

/**
 * Created by lv on 18-11-30.
 * 更多tab
 */

public class TabMoreFragment extends BaseLazyFragment implements View.OnClickListener {
    PullZoomRecyclerView refreshRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab_my_layout, container, false);
        refreshRecyclerView = view.findViewById(R.id.recyclerview_content);
        view.findViewById(R.id.close_dark_btn).setOnClickListener(this);
        view.findViewById(R.id.open_dark_btn).setOnClickListener(this);
        view.findViewById(R.id.open_first_act_btn).setOnClickListener(this);
        return view;
    }

    @Override
    public void loadData(long id) {

    }

    TabMoreAdapter mTabMoreAdapter;

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        ArrayList<SettingItem> dataList = new ArrayList<>();
        mTabMoreAdapter = new TabMoreAdapter(dataList, getContext());
        mTabMoreAdapter.init(refreshRecyclerView);
        SettingItem header = new SettingHeaderItem();
        header.itemType = 100;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRecyclerView.setAdapter(mTabMoreAdapter);
        refreshRecyclerView.setLayoutManager(layoutManager);
        mTabMoreAdapter.add(0, header);
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.open_dark_btn) {
            if (!SharedPreferencesHelper.getBoolean("APP_NIGHT_MODE", false)) {
                SharedPreferencesHelper.applyBoolean("APP_NIGHT_MODE", true);
                nightView = changeActNightMode(getActivity());
            }
        } else if (id == R.id.close_dark_btn) {
            if (SharedPreferencesHelper.getBoolean("APP_NIGHT_MODE", false)) {
                SharedPreferencesHelper.applyBoolean("APP_NIGHT_MODE", false);
                removeActNightMode(getActivity(), nightView);
            }
        } else if (id == R.id.open_first_act_btn) {
            Intent intent = new Intent(getContext(), FirstActivity.class);
            startActivity(intent);
        }
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
}
