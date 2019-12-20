package com.haste.lv.faith.ui.calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haste.lv.faith.R;
import com.haste.lv.faith.uiviews.caledarview.Calendar;
import com.haste.lv.faith.uiviews.caledarview.RangeMonthView;


/**
 * 范围选择月视图
 */

public class CustomRangeMonthView extends RangeMonthView {

    private int mRadius;

    public CustomRangeMonthView(Context context) {
        super(context);
    }


    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
                                     boolean isSelectedPre, boolean isSelectedNext) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (isSelectedPre) {
            if (isSelectedNext) {
                canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            } else {//最后一个，the last
                canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSelectedPaint);
                canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            }
        } else {
            if (isSelectedNext) {
                canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            }
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            //
        }

        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {

        final int day = calendar.getDay();
        mSchemeLunarTextPaint.setStyle(Paint.Style.FILL);
        mSchemeLunarTextPaint.setColor(0xff3cd3db);
        if (day == 7) {
            canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight / 2, mItemHeight / 2, mSchemeLunarTextPaint);
            canvas.drawRect(x + mItemWidth / 2, y, x + mItemWidth, y + mItemHeight, mSchemeLunarTextPaint);
        } else if (day == 14) {
            canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight / 2, mItemHeight / 2, mSchemeLunarTextPaint);
            canvas.drawRect(x , y, x + mItemWidth/2, y + mItemHeight, mSchemeLunarTextPaint);
        } else if (day > 7 && day < 14) {
            canvas.drawRect(x , y, x + mItemWidth, y + mItemHeight, mSchemeLunarTextPaint);
        } else {

            mSchemeLunarTextPaint.setColor(0xffbee914);
            canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight / 6, 8, mSchemeLunarTextPaint);
            mSchemeLunarTextPaint.setColor(0xffff8dbd);
            canvas.drawCircle(x + mItemWidth * 5 / 6, y + mItemHeight / 6, 8, mSchemeLunarTextPaint);
        }

        /////////
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        boolean isInRange = isInRange(calendar);
        boolean isEnable = !onCalendarIntercept(calendar);

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }

    }
}
