package com.haste.lv.faith.ui.maintab.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lv on 19-4-24.
 * 简单的测试列表使用的适配器
 */

public class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> datas;
    private Context mContext;

    public SimpleAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = new TextView(mContext);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 100);
        view.setLayoutParams(params);
        view.setTextSize(18);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.BLACK);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView view = (TextView) holder.itemView;
        view.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

}
