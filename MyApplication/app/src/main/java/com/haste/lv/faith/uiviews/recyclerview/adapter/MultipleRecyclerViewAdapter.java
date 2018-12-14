package com.haste.lv.faith.uiviews.recyclerview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;

import java.util.List;

/**
 * Created by lv on 18-12-6.
 * 多样式的recyclerView列表适配器
 * 1.把样式列表通过代理来进行管理
 *
 * setLayoutManager()必须放在setAdapter之前
 */

public class MultipleRecyclerViewAdapter<T> extends HelperStateRecyclerViewAdapter<T> {
    protected ItemViewDelegateManager mItemViewDelegateManager;
    public MultipleRecyclerViewAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public MultipleRecyclerViewAdapter(Context context, int... layoutIds) {
        super(context, layoutIds);
    }

    public MultipleRecyclerViewAdapter(List mList, Context context) {
        super(mList, context);
        this.mItemViewDelegateManager=new ItemViewDelegateManager();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);
        View view = inflateItemView(layoutId, parent);
        //因为Sticky也要用到tag,所有采用多tag的方式处理，产生一个唯一的key值
        CommonRecyclerViewHolder viewHolder = (CommonRecyclerViewHolder) view.getTag("holder".hashCode());
        if (viewHolder == null || viewHolder.getLayoutId() != layoutId) {
            viewHolder = createViewHolder(view, layoutId);
            return viewHolder;
        }
        return viewHolder;
    }
    public MultipleRecyclerViewAdapter addItemViewDelegate(IRecycleItemView<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultipleRecyclerViewAdapter addItemViewDelegate(int viewType, IRecycleItemView<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, T item) {
        mItemViewDelegateManager.convert(viewHolder,item,position);
    }

    @Override
    public int checkLayout(T item, int position) {
        return mItemViewDelegateManager.getItemViewType(item,position);
    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.refresh_view_default_empty_layout,parent);
    }

    @Override
    public View getErrorView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.refresh_view_default_error_layout,parent);
    }

    @Override
    public View getLoadingView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.refresh_view_default_loading_layout,parent);
    }
}
