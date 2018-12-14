package com.haste.lv.faith.mvpvm.loadstatus;

import android.content.Context;
import android.view.View;

/**
 * Created by lv on 18-12-12.
 * 负责显示原来的rootview，把新添加的状态页面进行隐藏(移除)
 */

public class SuccessViewStateControl extends BaseViewStateControl {
    public SuccessViewStateControl(Context context, View view, OnViewRefreshListener refreshListener){
        super(context,view,refreshListener);
    }
    @Override
    protected int onCreateView() {
        return 0;
    }
    public void show(){
        getRootView().setVisibility(View.VISIBLE);
    }
    public void showWithStateView(boolean successVisible){
        getRootView().setVisibility(successVisible?View.VISIBLE:View.INVISIBLE);
    }
}
