package com.haste.lv.faith.uiviews.recyclerview.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by lv on 18-12-6.
 * 万能适配Holder,减少赘于代码和加快开发流程
 */

public class CommonRecyclerViewHolder extends BaseRecyclerViewHolder {
    private SparseArray<View> mViews = new SparseArray<>();
    private View mConvertView;
    private int mLayoutId;

    public CommonRecyclerViewHolder(View itemView,int layoutId) {
        super(itemView);
        this.mLayoutId = layoutId;
        mConvertView = itemView;
        //因为Sticky也要用到tag,所有采用多tag的方式处理，产生一个唯一的key值
        mConvertView.setTag("holder".hashCode(),this);
    }

    @SuppressWarnings("unchecked")
    public <R extends View> R getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (R) view;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public View getItemView() {
        return mConvertView;
    }
}
