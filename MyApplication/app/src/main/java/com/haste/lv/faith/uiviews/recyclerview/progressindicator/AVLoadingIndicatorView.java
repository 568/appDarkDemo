package com.haste.lv.faith.uiviews.recyclerview.progressindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.haste.lv.faith.R;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallBeatIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallClipRotateIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallClipRotateMultipleIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallClipRotatePulseIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallGridBeatIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallGridPulseIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallPulseIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallPulseRiseIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallPulseSyncIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallRotateIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallScaleIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallScaleMultipleIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallScaleRippleIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallScaleRippleMultipleIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallSpinFadeLoaderIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallTrianglePathIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallZigZagDeflectIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BallZigZagIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.BaseIndicatorController;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.ClifeIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.CubeTransitionIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.LineScaleIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.LineScalePartyIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.LineScalePulseOutIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.LineScalePulseOutRapidIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.LineSpinFadeLoaderIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.PacmanIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.SemiCircleSpinIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.SquareSpinIndicator;
import com.haste.lv.faith.uiviews.recyclerview.progressindicator.indicator.TriangleSkewSpinIndicator;
import com.haste.lv.faith.utils.DisplayUtil;

/**
 * Created by lv on 18-12-6.
 * 各种样式效果的加载动画
 */

public class AVLoadingIndicatorView extends View {
    //indicators
    public static final int BallPulse = 0;
    public static final int BallGridPulse = 1;
    public static final int BallClipRotate = 2;
    public static final int BallClipRotatePulse = 3;
    public static final int SquareSpin = 4;
    public static final int BallClipRotateMultiple = 5;
    public static final int BallPulseRise = 6;
    public static final int BallRotate = 7;
    public static final int CubeTransition = 8;
    public static final int BallZigZag = 9;
    public static final int BallZigZagDeflect = 10;
    public static final int BallTrianglePath = 11;
    public static final int BallScale = 12;
    public static final int LineScale = 13;
    public static final int LineScaleParty = 14;
    public static final int BallScaleMultiple = 15;
    public static final int BallPulseSync = 16;
    public static final int BallBeat = 17;
    public static final int LineScalePulseOut = 18;
    public static final int LineScalePulseOutRapid = 19;
    public static final int BallScaleRipple = 20;
    public static final int BallScaleRippleMultiple = 21;
    public static final int BallSpinFadeLoader = 22;
    public static final int LineSpinFadeLoader = 23;
    public static final int TriangleSkewSpin = 24;
    public static final int Pacman = 25;
    public static final int BallGridBeat = 26;
    public static final int SemiCircleSpin = 27;
    public static final int ClifeIndicator = 28;

    @IntDef(flag = true, value = {BallPulse,
            BallGridPulse,
            BallClipRotate,
            BallClipRotatePulse,
            SquareSpin,
            BallClipRotateMultiple,
            BallPulseRise,
            BallRotate,
            CubeTransition,
            BallZigZag,
            BallZigZagDeflect,
            BallTrianglePath,
            BallScale,
            LineScale,
            LineScaleParty,
            BallScaleMultiple,
            BallPulseSync,
            BallBeat,
            LineScalePulseOut,
            LineScalePulseOutRapid,
            BallScaleRipple,
            BallScaleRippleMultiple,
            BallSpinFadeLoader,
            LineSpinFadeLoader,
            TriangleSkewSpin,
            Pacman,
            BallGridBeat,
            SemiCircleSpin, ClifeIndicator})
    public @interface Indicator {
    }

    public static final int DEFAULT_SIZE = 30;

    //attrs
    int mIndicatorId;
    int mIndicatorColor;

    Paint mPaint;

    BaseIndicatorController mIndicatorController;

    private boolean mHasAnimation;


    public AVLoadingIndicatorView(Context context) {
        this(context, null);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AVLoadingIndicatorView);
        mIndicatorId = a.getInt(R.styleable.AVLoadingIndicatorView_indicator, BallPulse);
        mIndicatorColor = a.getColor(R.styleable.AVLoadingIndicatorView_indicator_color, Color.WHITE);
        String indicatorName = a.getString(R.styleable.AVLoadingIndicatorView_indicator);
        if (!TextUtils.isEmpty(indicatorName)) {
            setIndicator(indicatorName);
        }
        a.recycle();
        mPaint = new Paint();
        mPaint.setColor(mIndicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        if (mIndicatorController != null) {
            //这里表示外部通过类名实例化样式的
            mIndicatorId = -1;
        }
        applyIndicator();
    }

    public void setIndicatorId(int indicatorId) {
        mIndicatorId = indicatorId;
        applyIndicator();
    }

    public void setIndicatorColor(int color) {
        mIndicatorColor = color;
        mPaint.setColor(mIndicatorColor);
        this.invalidate();
    }

    /**
     * You should pay attention to pass this parameter with two way:
     * for example:
     * 1. Only class Name,like "SimpleIndicator".(This way would use default package name with
     * "com.wang.avi.indicators")
     * 2. Class name with full package,like "com.my.android.indicators.SimpleIndicator".
     *
     * @param indicatorName the class must be extend Indicator .
     */
    public void setIndicator(String indicatorName) {
        if (TextUtils.isEmpty(indicatorName)) {
            return;
        }
        StringBuilder drawableClassName = new StringBuilder();
        if (!indicatorName.contains(".")) {
            String defaultPackageName = getClass().getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(indicatorName);
        try {
            Class<?> drawableClass = Class.forName(drawableClassName.toString());
            BaseIndicatorController indicator = (BaseIndicatorController) drawableClass.newInstance();
            setIndicator(indicator);
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setIndicator(BaseIndicatorController d) {
        if (mIndicatorController != d) {
            mIndicatorController = d;
            //need to set indicator color again if you didn't specified when you update the indicator .
            setIndicatorColor(mIndicatorColor);
            postInvalidate();
        }
    }

    private void applyIndicator() {
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        switch (mIndicatorId) {
            case BallPulse:
                mIndicatorController = new BallPulseIndicator();
                break;
            case BallGridPulse:
                mIndicatorController = new BallGridPulseIndicator();
                break;
            case BallClipRotate:
                mIndicatorController = new BallClipRotateIndicator();
                break;
            case BallClipRotatePulse:
                mIndicatorController = new BallClipRotatePulseIndicator();
                break;
            case SquareSpin:
                mIndicatorController = new SquareSpinIndicator();
                break;
            case BallClipRotateMultiple:
                mIndicatorController = new BallClipRotateMultipleIndicator();
                break;
            case BallPulseRise:
                mIndicatorController = new BallPulseRiseIndicator();
                break;
            case BallRotate:
                mIndicatorController = new BallRotateIndicator();
                break;
            case CubeTransition:
                mIndicatorController = new CubeTransitionIndicator();
                break;
            case BallZigZag:
                mIndicatorController = new BallZigZagIndicator();
                break;
            case BallZigZagDeflect:
                mIndicatorController = new BallZigZagDeflectIndicator();
                break;
            case BallTrianglePath:
                mIndicatorController = new BallTrianglePathIndicator();
                break;
            case BallScale:
                mIndicatorController = new BallScaleIndicator();
                break;
            case LineScale:
                mIndicatorController = new LineScaleIndicator();
                break;
            case LineScaleParty:
                mIndicatorController = new LineScalePartyIndicator();
                break;
            case BallScaleMultiple:
                mIndicatorController = new BallScaleMultipleIndicator();
                break;
            case BallPulseSync:
                mIndicatorController = new BallPulseSyncIndicator();
                break;
            case BallBeat:
                mIndicatorController = new BallBeatIndicator();
                break;
            case LineScalePulseOut:
                mIndicatorController = new LineScalePulseOutIndicator();
                break;
            case LineScalePulseOutRapid:
                mIndicatorController = new LineScalePulseOutRapidIndicator();
                break;
            case BallScaleRipple:
                mIndicatorController = new BallScaleRippleIndicator();
                break;
            case BallScaleRippleMultiple:
                mIndicatorController = new BallScaleRippleMultipleIndicator();
                break;
            case BallSpinFadeLoader:
                mIndicatorController = new BallSpinFadeLoaderIndicator();
                break;
            case LineSpinFadeLoader:
                mIndicatorController = new LineSpinFadeLoaderIndicator();
                break;
            case TriangleSkewSpin:
                mIndicatorController = new TriangleSkewSpinIndicator();
                break;
            case Pacman:
                mIndicatorController = new PacmanIndicator();
                break;
            case BallGridBeat:
                mIndicatorController = new BallGridBeatIndicator();
                break;
            case SemiCircleSpin:
                mIndicatorController = new SemiCircleSpinIndicator();
                break;
            case ClifeIndicator://在Clife动画时就让控件填满父控件
                this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mIndicatorController = new ClifeIndicator(getContext());
                break;
        }
        mIndicatorController.setTarget(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(DisplayUtil.dp2px(DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(DisplayUtil.dp2px(DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation) {
            mHasAnimation = true;
            applyAnimation();
        }
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.END);
            } else {
                mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.START);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.CANCEL);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.START);
    }

    void drawIndicator(Canvas canvas) {
        mIndicatorController.draw(canvas, mPaint);
    }

    void applyAnimation() {
        mIndicatorController.initAnimation();
    }

}
