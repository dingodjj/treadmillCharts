package com.nl.treadmilllauncher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.utils.CommonUtils;

/**
 * Created by dingo on 2018/3/5.
 */

public class InstrumentPanelView extends View {

    private static final String TAG = "InstrumentPanelView";

    private String txt_en = "";
    private String txt_cn = "";
    private int color_start = Color.parseColor("#34EAAB");
    private int color_end = Color.parseColor("#1D717B");
    private int max_value = 10;
    private int current_value = 0;
    private int subTextColor = Color.parseColor("#0aFFFFFF");
    private int mainTextColor = Color.parseColor("#FFFFFF");
    private float textSize = 0;

    private float borderWidth = 15f;
    private float lineLength = 0f;
    private float startAngle = 180;
    private float angleLength = 180;
    private float currentAngleLength = 0;
    private int animationLength = 500;

    private float centerX, centerY, radius;
    private Paint scalePaint, progressPaint, arcPaint, textCnPaint, textValuePaint, textEnPaint;

    public InstrumentPanelView(Context context) {
        super(context);
    }

    public InstrumentPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InstrumentPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.InstrumentPanelView);

            txt_en = typedArray.getString(R.styleable.InstrumentPanelView_txt_en);
            txt_cn = typedArray.getString(R.styleable.InstrumentPanelView_txt_cn);
            max_value = typedArray.getInt(R.styleable.InstrumentPanelView_max_value, 10);
            current_value = typedArray.getInt(R.styleable.InstrumentPanelView_current_value, 0);
            color_start = typedArray.getColor(R.styleable.InstrumentPanelView_color_start, color_start);
            color_end = typedArray.getColor(R.styleable.InstrumentPanelView_color_end, color_end);
            textSize = typedArray.getFloat(R.styleable.InstrumentPanelView_textSize, getResources().getDimension(R.dimen.x47));
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        if(current_value > max_value || max_value == 0){
            currentAngleLength = 360;
        }else {
            float scale = ( current_value + 0f ) / ( max_value + 0f );
            currentAngleLength = scale * angleLength;
        }

        scalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scalePaint.setStyle(Paint.Style.FILL);
        scalePaint.setStrokeWidth(1);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(0x4DFFFFFF);
        progressPaint.setStyle(Paint.Style.STROKE);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);//圆角弧度
        arcPaint.setStyle(Paint.Style.STROKE);//设置填充样式

        textCnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textCnPaint.setColor(mainTextColor);
        textCnPaint.setTextSize(textSize * 0.67f);
        textCnPaint.setTextAlign(Paint.Align.CENTER);

        textValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textValuePaint.setTextAlign(Paint.Align.CENTER);
        textValuePaint.setTypeface(CommonUtils.getTfByPath(getContext(), "fonts/MyriadPro-BoldCond.otf"));
        textValuePaint.setColor(mainTextColor);
        textValuePaint.setTextSize(textSize);

        textEnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textEnPaint.setTextAlign(Paint.Align.CENTER);
        textEnPaint.setColor(subTextColor);
        textEnPaint.setTextSize(textSize * 1.2f);
        textEnPaint.setTypeface(CommonUtils.getTfByPath(getContext(), "fonts/airstrike.ttf"));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        radius = width / 2 > height ? height : width / 2;
        lineLength = radius * 0.06f;

        centerX = width / 2;
        centerY = height;

        drawScale(canvas);
        drawProgress(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        canvas.drawText(txt_cn, centerX, getHeight() - radius * 0.45f, textCnPaint);

        canvas.drawText(current_value + "", centerX, getHeight() - radius * 0.13f, textValuePaint);

        canvas.drawText(txt_en, centerX, getHeight() - radius * 0.16f, textEnPaint);
    }

    private void drawProgress(Canvas canvas) {
        RectF rectF = new RectF(centerX - radius + 2 * lineLength, centerY - radius + 2 * lineLength,
                centerX + radius - 2 * lineLength, centerY + radius - 2 * lineLength);

        progressPaint.setStrokeWidth(lineLength);
        canvas.drawArc(rectF, 180, 360, false, progressPaint);

        SweepGradient mSweepGradient = new SweepGradient(centerX, centerX, new int[]{ color_start, color_end,
                color_start }, new float[]{0, 0.48f, 1f});
        arcPaint.setShader(mSweepGradient);
        arcPaint.setStrokeWidth(lineLength);
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, arcPaint);
    }

    private void drawScale(Canvas canvas) {
        scalePaint.setStrokeWidth(2);
        for (int i = 0; i < 31; i++) {
            canvas.save();
            canvas.translate(centerX, centerY);
            canvas.rotate(90 - 6 * i);

            scalePaint.setColor(subTextColor);
            canvas.drawLine(0, - radius, 0, - radius + lineLength * 0.75f, scalePaint);

            scalePaint.setColor(mainTextColor);
            canvas.drawLine(0, - radius + 13 * lineLength / 4f, 0, - radius + 17 * lineLength / 4f, scalePaint);

            canvas.restore();
        }
    }

    public void setCurrentCount(int max, int curValue) {

        if (curValue > max) {
            curValue = max;
        }

        this.max_value = max;
        this.current_value = curValue;

        float scale = ( curValue + 0f ) / ( max + 0f );
        float oldAngleLength = currentAngleLength;
        currentAngleLength = scale * angleLength;

        /**开始执行动画*/
        setAnimation(oldAngleLength, currentAngleLength, animationLength);
    }

    private void setAnimation(float old, float current, int length) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(old, current);;
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngleLength);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**每次要绘制的圆弧角度**/
                currentAngleLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }
}
