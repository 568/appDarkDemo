package com.haste.lv.faith.uiviews.recyclerview.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lv on 18-12-14.
 */
public class CustomRefreshHeader extends BaseRefreshHeader {
    private CustomAnimView customAnimView;

    public CustomRefreshHeader(Context context) {
        super(context);
    }

    public CustomRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getView() {
        customAnimView=new CustomAnimView(getContext());
        return customAnimView;
    }

    @Override
    public void setState(int state) {
        super.setState(state);
        //选择自定义需要处理的状态：STATE_NORMAL、STATE_RELEASE_TO_REFRESH、STATE_REFRESHING、STATE_DONE
        if (state == STATE_REFRESHING) {    // 显示进度
            //这里处理自己的逻辑、刷新中
            customAnimView.startAnim();
        } else if (state == STATE_DONE) {
            //这里处理自己的逻辑、刷新完成
            customAnimView.stopAnim();
        } else {
            customAnimView.startAnim();
        }
    }
}
