package com.haste.lv.faith.ui.maintab.childs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haste.lv.faith.R;
import com.haste.lv.faith.mvpvm.base.BaseAbsLifecycleFragment;

/**
 * Created by lv on 19-4-28.
 * 发现tab简单的内容页面的Fragment
 */
public class DiscoverySimpleFragment extends BaseAbsLifecycleFragment {
    @Override
    public void loadData(long id) {

    }
    private static final String ARG_SELECTION_NUM = "arg_selection_num"; // 参数的Tag

    // 显示的文本信息
    private static final int[] TEXTS = {
            R.string.tiffany_text,
            R.string.taeyeon_text,
            R.string.yoona_text
    };

    TextView mTvText;

    public DiscoverySimpleFragment() {
    }

    /**
     * 通过静态接口创建Fragment，规范参数的使用
     *
     * @param selectionNum 参数
     * @return 创建的Fragment
     */
    public static DiscoverySimpleFragment newInstance(int selectionNum) {
        DiscoverySimpleFragment simpleFragment = new DiscoverySimpleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SELECTION_NUM, selectionNum);
        simpleFragment.setArguments(args);
        return simpleFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabdiscovery_card_fragment_layout, container, false);
        mTvText=view.findViewById(R.id.main_tv_text);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvText.setText(TEXTS[getArguments().getInt(ARG_SELECTION_NUM)]); // 设置文本信息
    }

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }
}
