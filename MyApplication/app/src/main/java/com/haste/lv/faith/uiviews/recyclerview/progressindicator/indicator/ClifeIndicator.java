package com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;


import com.haste.lv.faith.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by lv on 18-12-6.
 */

public class ClifeIndicator extends BaseIndicatorController {
    private int[] loading = {R.mipmap.loading_0, R.mipmap.loading_1, R.mipmap.loading_2};
    private int frameInt = 0;
    private Context mContext;
    private float ratioW = 1;
    private float ratioH = 1;

    public ClifeIndicator(Context context) {
        super();
        mContext = context;
    }

    boolean isFrist = true;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        Matrix matrix = new android.graphics.Matrix();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), loading[frameInt % 3]);
        if(bitmap==null)return;
        if(isFrist){
            ratioW = ((float) getWidth()) / bitmap.getWidth();
            ratioH = ((float) getHeight()) / bitmap.getHeight();
            isFrist = false;
        }
        matrix.setScale(ratioW, ratioH);
        //canvas.scale(ratioW, ratioH);
        canvas.drawBitmap(bitmap, matrix, paint);
        bitmap.recycle();
        bitmap = null;
        canvas.restore();
    }

    @Override
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList<>();
        ValueAnimator frameAnim = ValueAnimator.ofInt(1, 10);
        frameAnim.setDuration(1000);
        frameAnim.setInterpolator(new LinearInterpolator());
        frameAnim.setRepeatCount(ValueAnimator.INFINITE);
        frameAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (value != frameInt) {
                    postInvalidate();
                    //Log.e("test", "======frameInt========" + frameInt);
                }
                frameInt = value;
            }
        });
        frameAnim.start();
        animators.add(frameAnim);
        return animators;
    }
}
