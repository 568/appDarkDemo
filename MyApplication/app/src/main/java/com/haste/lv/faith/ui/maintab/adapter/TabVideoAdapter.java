package com.haste.lv.faith.ui.maintab.adapter;

import android.content.Context;

import com.haste.lv.faith.module.videoplayer.model.VideoVO;
import com.haste.lv.faith.module.videoplayer.viewholder.ItemVideoAdvertHolder;
import com.haste.lv.faith.module.videoplayer.viewholder.ItemVideoNormalHolder;
import com.haste.lv.faith.ui.maintab.bean.VideoItem;
import com.haste.lv.faith.uiviews.recyclerview.adapter.MultipleRecyclerViewAdapter;

import java.util.List;

/**
 * Created by lv on 18-12-13.
 * 视频列表适配器
 */
public class TabVideoAdapter extends MultipleRecyclerViewAdapter<VideoVO> {
    public TabVideoAdapter(List mList, Context context) {
        super(mList, context);
        addItemViewDelegate(new ItemVideoNormalHolder());
        addItemViewDelegate(new ItemVideoAdvertHolder());
    }
}
