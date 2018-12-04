package com.haste.lv.faith.utils;

import com.haste.lv.faith.core.APPApplication;

/**
 * Created by lv on 18-12-4.
 */

public class DisplayUtil {
    public static int dp2px(float dp) {
        final float scale = APPApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int sp2px(float sp) {
        final float scale = APPApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
