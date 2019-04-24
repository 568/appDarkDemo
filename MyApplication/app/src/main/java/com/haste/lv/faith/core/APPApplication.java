package com.haste.lv.faith.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.haste.lv.faith.utils.SharedPreferencesHelper;
import com.shuyu.gsyvideoplayer.utils.Debuger;

import java.util.WeakHashMap;

/**
 * Created by lv on 18-11-21.
 */

public class APPApplication extends Application {
    private static Context instance;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance=getApplicationContext();
        SharedPreferencesHelper.init(getApplicationContext(), "app_globle_sp");
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (null != activity && !TextUtils.equals(activity.getClass().getSimpleName(), "MainActivity"))
                    checkNightMode(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (nightModeHashMap.containsKey(activity)) {
                    nightModeHashMap.remove(activity);
                }
                AppManager.getAppManager().removeActivity(activity);
            }
        });
        //
        Debuger.enable();
        DoraemonKit.install(this);
    }

    public static Context getContext(){
        return instance;
    }
    private WeakHashMap<Activity, View> nightModeHashMap = new WeakHashMap<>();

    public void checkNightMode(Activity activity) {
        if (activity == null) return;
        if (SharedPreferencesHelper.getBoolean("APP_NIGHT_MODE", false)) {
            if (!nightModeHashMap.containsKey(activity))
                nightModeHashMap.put(activity, changeActNightMode(activity));
        } else {
            if (nightModeHashMap.containsKey(activity)) {
                removeActNightMode(activity, nightModeHashMap.get(activity));
                nightModeHashMap.remove(activity);
            }
        }
    }


    private View changeActNightMode(Activity activity) {
        WindowManager mWindowManager = activity.getWindowManager();//(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams nightViewParam = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                PixelFormat.TRANSPARENT);
        nightViewParam.width = ViewGroup.LayoutParams.MATCH_PARENT;
        nightViewParam.height = ViewGroup.LayoutParams.MATCH_PARENT;
        nightViewParam.gravity = Gravity.CENTER;
        View nightView = new View(this);
        nightView.setBackgroundColor(0x66000000);
        mWindowManager.addView(nightView, nightViewParam);
        return nightView;

    }

    private void removeActNightMode(Activity activity, View view) {
        if (null == view)
            return;
        WindowManager mWindowManager = activity.getWindowManager();// (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.removeViewImmediate(view);
    }
}
