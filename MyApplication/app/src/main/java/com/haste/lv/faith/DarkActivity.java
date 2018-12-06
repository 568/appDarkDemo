package com.haste.lv.faith;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.haste.lv.faith.core.HFBaseActivity;

/**
 * Created by lv on 18-11-21.
 */

public class DarkActivity extends HFBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dark);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
