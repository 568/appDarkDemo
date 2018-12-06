package com.haste.lv.faith.uiviews.recyclerview.adapter;

/**
 * Created by lv on 18-12-6.
 */

public interface IRecycleItemView<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(HelperRecyclerViewHolder holder, T t, int position);

    void onCreateView(HelperRecyclerViewHolder holder);
}
