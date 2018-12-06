package com.haste.lv.faith.ui.maintab.itemdelegate;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.haste.lv.faith.R;
import com.haste.lv.faith.ui.maintab.bean.SettingItem;
import com.haste.lv.faith.ui.maintab.views.PullZoomRecyclerView;
import com.haste.lv.faith.uiviews.recyclerview.PullToRefreshRecyclerView;
import com.haste.lv.faith.uiviews.recyclerview.adapter.HelperRecyclerViewHolder;
import com.haste.lv.faith.uiviews.recyclerview.adapter.IRecycleItemView;

/**
 * Created by lv on 18-12-6.
 * '我的'tab页面的头部样式视图
 * 注意：由于头部使用了视差效果的处理，所以需要外部把主视图传进来
 */

public class MineHeaderItemView implements IRecycleItemView<SettingItem> {
    PullZoomRecyclerView mRecyclerView;
    private ImageView zoomView;
    private ViewGroup zoomHeaderContainer;
    public MineHeaderItemView(PullZoomRecyclerView view) {
        this.mRecyclerView = view;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.moretab_item_pullzoom_header;
    }

    @Override
    public boolean isForViewType(SettingItem item, int position) {
        return item.itemType == 100;
    }

    @Override
    public void convert(HelperRecyclerViewHolder holder, SettingItem settingItem, int position) {
        if (zoomView==null) {
            zoomView=holder.getItemView().findViewById(R.id.zoom_image_view);
            mRecyclerView.setZoomView(zoomView);
        }
        if (zoomHeaderContainer==null) {
            zoomHeaderContainer=holder.getItemView().findViewById(R.id.zoom_header_container);
            mRecyclerView.setHeaderContainer(zoomHeaderContainer);
        }
    }

    @Override
    public void onCreateView(HelperRecyclerViewHolder holder) {

    }
}
