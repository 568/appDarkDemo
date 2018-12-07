package com.haste.lv.faith.ui.maintab.itemdelegate;

import com.haste.lv.faith.R;
import com.haste.lv.faith.ui.maintab.bean.SettingItem;
import com.haste.lv.faith.uiviews.recyclerview.adapter.HelperRecyclerViewHolder;
import com.haste.lv.faith.uiviews.recyclerview.adapter.IRecycleItemView;

/**
 * Created by lv on 18-12-7.
 * 更多页面，带选择开关按钮的item样式
 */

public class MineNormalTitleItemView implements IRecycleItemView<SettingItem> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.setting_view_basic_item_h;
    }

    @Override
    public boolean isForViewType(SettingItem item, int position) {
        return item.itemType==2;
    }

    @Override
    public void convert(HelperRecyclerViewHolder holder, SettingItem settingItem, int position) {
        holder.setText(R.id.setting_view_basic_item_h_title,settingItem.title);
        holder.setText(R.id.setting_view_basic_item_h_subtitle,settingItem.subTitle);
    }

    @Override
    public void onCreateView(HelperRecyclerViewHolder holder) {

    }
}
