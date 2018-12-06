package com.haste.lv.faith.ui.maintab.adapter;

import android.content.Context;

import com.haste.lv.faith.ui.maintab.bean.SettingItem;
import com.haste.lv.faith.ui.maintab.itemdelegate.MineHeaderItemView;
import com.haste.lv.faith.ui.maintab.views.PullZoomRecyclerView;
import com.haste.lv.faith.uiviews.recyclerview.adapter.MultipleRecyclerViewAdapter;

import java.util.List;

/**
 * Created by lv on 18-12-6.
 * app底部我的(更多)tab页的列表适配器
 */

public class TabMoreAdapter extends MultipleRecyclerViewAdapter<SettingItem> {

    public TabMoreAdapter(List mList, Context context) {
        super(mList, context);

    }
    public void init(PullZoomRecyclerView zoomRecyclerView){
        addItemViewDelegate(new MineHeaderItemView(zoomRecyclerView));
    }
}
