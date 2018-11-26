package com.example.lv.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.lv.myapplication.utils.SharedPreferencesHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    View nightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.close_dark_btn).setOnClickListener(this);
        findViewById(R.id.open_dark_btn).setOnClickListener(this);
        findViewById(R.id.open_first_act_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.open_dark_btn) {
            if (!SharedPreferencesHelper.getBoolean("APP_NIGHT_MODE", false)) {
                SharedPreferencesHelper.applyBoolean("APP_NIGHT_MODE", true);
                nightView = changeActNightMode(this);
            }
        } else if (id == R.id.close_dark_btn) {
            if (SharedPreferencesHelper.getBoolean("APP_NIGHT_MODE", false)) {
                SharedPreferencesHelper.applyBoolean("APP_NIGHT_MODE", false);
                removeActNightMode(this, nightView);
            }
        } else if (id == R.id.open_first_act_btn) {
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
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
