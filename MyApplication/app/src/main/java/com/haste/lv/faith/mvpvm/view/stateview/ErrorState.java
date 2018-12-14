package com.haste.lv.faith.mvpvm.view.stateview;

import android.content.Context;
import android.view.View;

import com.haste.lv.faith.R;
import com.haste.lv.faith.mvpvm.loadstatus.BaseViewStateControl;

/**
 * Created by lv on 18-12-12.
 */

public class ErrorState extends BaseViewStateControl {
    @Override
    protected int onCreateView() {
        return R.layout.refresh_view_default_error_layout;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);
        if (view.getTag() != null) {
            //可以根据tag进行错误判断，显示不同的文案等逻辑(如：网络中断||解析出错||...)
        }
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }
}
