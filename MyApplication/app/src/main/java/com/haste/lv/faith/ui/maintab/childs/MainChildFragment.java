package com.haste.lv.faith.ui.maintab.childs;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haste.lv.faith.R;
import com.haste.lv.faith.ui.BaseLazyRxFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-11-30.
 */

public class MainChildFragment extends BaseLazyRxFragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecylerView;
    protected LinearLayoutManager mManager;
    protected TestAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maintab_mainchild_layout, container, false);

        mRecylerView = rootView.findViewById(R.id.recylerView);
        mRefreshLayout = rootView.findViewById(R.id.swipe);

        mManager = new LinearLayoutManager(getActivity());
        mRecylerView.setLayoutManager(mManager);
        mRecylerView.setItemAnimator(new DefaultItemAnimator());

        mRecylerView.setAdapter(adapter = new TestAdapter(getActivity()));
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("data" + i);
        }

        adapter.setDatas(strings);
        mRefreshLayout.setEnabled(false);
        mRefreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void loadData(long id) {

    }

    @Override
    public void onRefresh() {
        this.mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public void setHeadState(boolean canScroll) {
        if (!canScroll) {
            if (mRecylerView != null) {
                //要禁用，scrollToPosition才有效
                mRefreshLayout.setEnabled(false);
                mRecylerView.getLayoutManager().scrollToPosition(0);
            }
        } else {
            mRefreshLayout.setEnabled(true);
        }
    }

    public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> datas;
        private Context mContext;

        public TestAdapter(Context context) {
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

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }
}
