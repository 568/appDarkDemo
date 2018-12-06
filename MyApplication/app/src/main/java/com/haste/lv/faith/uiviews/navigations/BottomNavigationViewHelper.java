package com.haste.lv.faith.uiviews.navigations;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

/**
 * Created by lv on 18-11-30.
 * 当菜单项多于3个时，效果和3个及以下的效果已经完全不一样了
 * 利用反射改变mShiftingMode值为true
 * //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
 * BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
 */

public class BottomNavigationViewHelper {

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShifting(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException e) {
            //Unable to get shift mode fiel
        } catch (IllegalAccessException e) {
            //Unable to change value of shift mode
        }
    }
}
