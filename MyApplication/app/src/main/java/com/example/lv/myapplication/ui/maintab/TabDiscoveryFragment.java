package com.example.lv.myapplication.ui.maintab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lv.myapplication.R;
import com.example.lv.myapplication.ui.BaseLazyFragment;

/**
 * Created by lv on 18-11-30.
 */

public class TabDiscoveryFragment extends BaseLazyFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab_discovery_layout, container, false);
        return view;
    }

    @Override
    public void loadData(long id) {

    }
}
