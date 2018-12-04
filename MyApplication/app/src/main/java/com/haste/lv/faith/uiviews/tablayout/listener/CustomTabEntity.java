package com.haste.lv.faith.uiviews.tablayout.listener;

import android.support.annotation.DrawableRes;

/**
 * Created by lv on 18-12-4.
 */

public interface CustomTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}
