package com.haste.lv.faith;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.haste.lv.faith.core.HFBaseActivity;

/**
 * Created by lv on 18-11-21.
 */

public class FirstActivity extends HFBaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        findViewById(R.id.goback_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.goback_btn) {
            finish();
        }
    }
}
