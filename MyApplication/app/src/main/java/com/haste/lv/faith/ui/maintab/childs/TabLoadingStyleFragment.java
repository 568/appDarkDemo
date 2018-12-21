package com.haste.lv.faith.ui.maintab.childs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;
import com.haste.lv.faith.mvpvm.base.BaseAbsLifecycleFragment;

/**
 * Created by lv on 18-12-17.
 * 加载动画的样式展示
 * 1.布局使用流式布局，手动追加的，如果有新的样式实验，继续添加子视图即可
 */

public class TabLoadingStyleFragment extends BaseAbsLifecycleFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab_loadingstyle_layout, container, false);
        return view;
    }
    @Override
    public void loadData(long id) {

    }
}