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

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(final float pxValue) {
        final float scale = APPApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(final float spValue) {
        final float fontScale = APPApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = APPApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
