package com.haste.lv.faith.ui.maintab.itemdelegate;

import com.haste.lv.faith.R;
import com.haste.lv.faith.ui.maintab.bean.SettingItem;
import com.haste.lv.faith.ui.maintab.viewmodel.SettingViewModel;
import com.haste.lv.faith.uiviews.recyclerview.adapter.HelperRecyclerViewHolder;
import com.haste.lv.faith.uiviews.recyclerview.adapter.IRecycleItemView;
import com.haste.lv.faith.uiviews.switchbutton.SwitchButton;

/**
 * Created by lv on 18-12-7.
 * 更多页面，带选择开关按钮的item样式
 */

public class MineNorSwitchBtnItemView implements IRecycleItemView<SettingItem> {
    private SettingViewModel mSettingViewModel;
    public MineNorSwitchBtnItemView(SettingViewModel viewModel){
        this.mSettingViewModel=viewModel;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.setting_view_switch_item;
    }

    @Override
    public boolean isForViewType(SettingItem item, int position) {
        return item.itemType==1;
    }

    @Override
    public void convert(HelperRecyclerViewHolder holder, final SettingItem settingItem, int position) {
        holder.setText(R.id.setting_view_switch_item_title,settingItem.title);
        final SwitchButton mSwitchButton =holder.getView(R.id.setting_view_switch_item_switch);
        mSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settingItem.isChecked=isChecked;
                if (null!=mSettingViewModel){
                    mSettingViewModel.getCurrentSettingData().setValue(settingItem);
                }
            }
        });
    }

    @Override
    public void onCreateView(HelperRecyclerViewHolder holder) {

    }
}
