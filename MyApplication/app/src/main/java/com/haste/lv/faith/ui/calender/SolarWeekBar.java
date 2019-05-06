package com.haste.lv.faith.ui.calender;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.haste.lv.faith.R;
import com.haste.lv.faith.uiviews.caledarview.WeekBar;


/**
 * 自定义英文栏
 */

public class SolarWeekBar extends WeekBar {

    public SolarWeekBar(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.solar_week_bar, this, true);
        setBackgroundColor(0xffcdcdcd);
    }

    /**
     * 当周起始发生变化，使用自定义布局需要重写这个方法，避免出问题
     *
     * @param weekStart 周起始
     */
    @Override
    protected void onWeekStartChange(int weekStart) {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setText(getWeekString(i, weekStart));
        }
    }

    /**
     * 或者周文本，这个方法仅供父类使用
     *
     * @param index     index
     * @param weekStart weekStart
     * @return 或者周文本
     */
    private String getWeekString(int index, int weekStart) {
        String[] weeks = getContext().getResources().getStringArray(R.array.english_week_string_array);

        if (weekStart == 1) {
            return weeks[index];
        }
        if (weekStart == 2) {
            return weeks[index == 6 ? 0 : index + 1];
        }
        return weeks[index == 0 ? 6 : index - 1];
    }
}
