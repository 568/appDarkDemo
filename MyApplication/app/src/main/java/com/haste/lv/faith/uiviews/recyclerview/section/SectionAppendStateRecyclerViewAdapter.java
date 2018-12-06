package com.haste.lv.faith.uiviews.recyclerview.section;

import android.content.Context;

import com.haste.lv.faith.uiviews.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * Created by lv on 18-12-6.
 * 提供分组便捷操作,可设置状态的错误页面、空页面、加载中页面、内容页面自由切换</p>
 * 追加页面状态方式，例如格子页面一部分是本地写死的内容，还有一部分在最后面需要网络请求返回，主要针对这种状态。
 */

public abstract class SectionAppendStateRecyclerViewAdapter<T> extends SectionStateRecyclerViewAdapter<T>{
    public SectionAppendStateRecyclerViewAdapter(Context context, List list) {
        super(context, list);
    }

    public SectionAppendStateRecyclerViewAdapter(Context context) {
        super(context);
    }

    int count = 0;

    public void setState(@State int state) {
        this.state = state;
        switch (state) {
            case STATE_LOADING:
            case STATE_EMPTY:
            case STATE_ERROR:
                if(xRecyclerView!=null)
                    xRecyclerView.setEnabledScroll(false);
                count = 1;
                break;
            case STATE_NORMAL:
                if(xRecyclerView!=null)
                    xRecyclerView.setEnabledScroll(true);
                count = 0;
                break;
        }
        notifyDataSetChanged();
    }

    public int getState() {
        return state;
    }

    @Override
    public int getItemCount() {
        return super.itemCount() + count;
    }


    @Override
    public int getItemViewType(int position) {
        if (count == 1 && (position + count) == getItemCount()) {
            switch (state) {
                case STATE_LOADING:
                    return TYPE_LOADING;
                case STATE_EMPTY:
                    return TYPE_EMPTY;
                case STATE_ERROR:
                    return TYPE_ERROR;
            }
        }
        return itemViewType(position);
    }

    @Override
    public void onBindViewHolder(HelperRecyclerViewHolder viewHolder, int position) {
        if (count == 1 && (position + count) == getItemCount()) {
            switch (state) {
                case STATE_LOADING:
                    onBindLoadingViewHolder(viewHolder);
                    break;
                case STATE_EMPTY:
                    onBindEmptyViewHolder(viewHolder);
                    break;
                case STATE_ERROR:
                    onBindErrorViewHolder(viewHolder);
                    break;
            }
        } else {
            viewHolder(viewHolder, position);
        }
    }

    /**
     * 追加内容  如果发现有内容追加过来，会自动消掉占位图
     */
    public boolean addAppendGroups(List<T> datas) {
        boolean result = super.addGroups(datas);
        setState(STATE_NORMAL);
        return result;
    }

    /**
     * 追加内容  如果发现有内容追加过来，会自动消掉占位图
     */
    public boolean addAppendGroup(T data) {
        boolean result = super.addGroup(data);
        setState(STATE_NORMAL);
        return result;
    }
}
