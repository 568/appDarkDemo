package com.haste.lv.faith.uiviews.recyclerview.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by lv on 18-12-6.
 * 设置拖拽监听
 */

public interface OnItemDragListener {
    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos);

    void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to);

    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos);
}
