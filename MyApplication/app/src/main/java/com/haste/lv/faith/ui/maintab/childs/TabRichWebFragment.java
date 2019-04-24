package com.haste.lv.faith.ui.maintab.childs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;
import com.haste.lv.faith.mvpvm.base.BaseAbsLifecycleFragment;
import com.haste.lv.faith.ui.maintab.adapter.SimpleAdapter;
import com.haste.lv.faith.uiviews.viewpager.HeaderScrollHelper;
import com.haste.lv.faith.uiviews.viewpager.HeaderViewPager;
import com.haste.lv.faith.uiviews.web.RichWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 19-4-24.
 */

public class TabRichWebFragment extends BaseAbsLifecycleFragment {
    private RichWebView webView;
    private RecyclerView recyclerView;
    private HeaderViewPager scrollableLayout;//滚动控件父容器

    @Override
    public void loadData(long id) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maintab_richweb_layout, container, false);
        webView = rootView.findViewById(R.id.web_view);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        scrollableLayout = rootView.findViewById(R.id.scrollableLayout);
        scrollableLayout.setCurrentScrollableContainer(new HeaderScrollHelper.ScrollableContainer() {
            @Override
            public View getScrollableView() {
                return recyclerView;
            }
        });
        return rootView;
    }
    SimpleAdapter adapter;
    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        webView.loadUrl("https://github.com/568/appDarkDemo");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter = new SimpleAdapter(getActivity()));
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("data" + i);
        }

        adapter.setDatas(strings);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearCache(true);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
