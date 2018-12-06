package com.haste.lv.faith.uiviews.recyclerview.adapter;

import android.support.v4.util.SparseArrayCompat;

/**
 * 编写人:lv
 * 创建时间: 18/12/01
 * 描述:多样式的代理管理者
 * 备注:
 */
public class ItemViewDelegateManager<T> {
    /*整个列表总共的样式,需要提前设定*/
    SparseArrayCompat<IRecycleItemView<T>> delegates = new SparseArrayCompat();

    /**
     * 返回样式数量
     */
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    /**
     * 添加列表样式,必须是约定的(实现了IRecycleItemView接口)
     *
     * @time 16/12/12 15:22
     */
    public ItemViewDelegateManager<T> addDelegate(IRecycleItemView<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }
    /**
     * 添加列表样式,必须是约定的(实现了IRecycleItemView接口)
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, IRecycleItemView<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }
    /**
     * 移除列表样式,必须是约定的(实现了IRecycleItemView接口)
     */
    public ItemViewDelegateManager<T> removeDelegate(IRecycleItemView<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }
    /**
     * 添加列表样式(根据制定的type),必须是约定的(实现了IRecycleItemView接口)
     */
    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 获取指定位置的列表样式,必须是约定的(实现了IRecycleItemView接口)
     */
    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            IRecycleItemView<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }
    /**
     * 样式视图的转化
     */
    public void convert(HelperRecyclerViewHolder holder, T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            IRecycleItemView<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    /**
     * 样式视图的转化
     */
    public void onCreate(HelperRecyclerViewHolder holder,int viewType) {
        delegates.get(viewType).onCreateView(holder);
    }
    public int getItemViewLayoutId(int viewType) {
        return delegates.get(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(IRecycleItemView itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }
}
