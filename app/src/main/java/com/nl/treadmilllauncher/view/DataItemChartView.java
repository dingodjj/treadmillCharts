package com.nl.treadmilllauncher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.utils.CommonUtils;

/**
 * Created by dingo on 2018/3/19.
 */

public class DataItemChartView extends View {

    private int start_color = 0xffD5FD18, end_color = 0xff0ADAFA;
    private int dark_color = 0x1affffff;
    private float textSize = 30f;
    private int text_color = 0xffffffff;

    private Drawable icon;
    private Paint circlePaint, ringPaint, textPaint;
    private RectF rectF;
    private Rect rect;

    private float startAngle = -90;
    private float angleLength = 360;
    private float currentAngleLength = -360;
    private int animationLength = 1000;

    private String value = "0.0";

    public DataItemChartView(Context context) {
        super(context);
    }

    public DataItemChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DataItemChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DataItemChartView);

                textSize = typedArray.getFloat(R.styleable.DataItemChartView_textSize, 30.f);
                start_color = typedArray.getColor(R.styleable.DataItemChartView_start_color, start_color);
                end_color = typedArray.getColor(R.styleable.DataItemChartView_end_color, end_color);
            } finally {
                if (typedArray != null)
                    typedArray.recycle();
            }
        }

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(dark_color);
        circlePaint.setStyle(Paint.Style.STROKE);

        ringPaint = new Paint();
        ringPaint.setStrokeJoin(Paint.Join.ROUND);
        ringPaint.setStrokeCap(Paint.Cap.ROUND);//圆角弧度
        ringPaint.setStyle(Paint.Style.STROKE);//设置填充样式
        ringPaint.setAntiAlias(true);//抗锯齿功能

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(text_color);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(CommonUtils.getTfByPath(getContext(), "fonts/MyriadPro-BoldCond.otf"));

        rectF = new RectF();
        rect = new Rect();

        icon = getResources().getDrawable(R.mipmap.summary_heart_rate);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        rectF.set(width * 0.0184f, width * 0.0184f, width * 0.9816f, width * 0.9816f);
        circlePaint.setStrokeWidth(width * 0.0364f);
        ringPaint.setStrokeWidth(width * 0.0364f);

        if(ringPaint.getShader() == null){
            SweepGradient mSweepGradient = new SweepGradient(width / 2f, height / 2f, new int[]{ start_color,
                    end_color}, null);
            //旋转渐变
            Matrix matrix = new Matrix();
            matrix.setRotate(-85f, canvas.getWidth() / 2, canvas.getHeight() / 2);
            mSweepGradient.setLocalMatrix(matrix);
            ringPaint.setShader(mSweepGradient);
        }

        canvas.drawArc(rectF, 0, 360,false, circlePaint);
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, ringPaint);

        float textWidth = textPaint.measureText(value);
        float textHeight = (textPaint.descent() + textPaint.ascent()) / 2;
        canvas.drawText(value, width / 2f, height * 0.418f - textHeight, textPaint);

        if(icon != null){
            int iconW = icon.getIntrinsicWidth();
            int iconH = icon.getIntrinsicHeight();
            icon.setBounds((int)(width / 2f - iconW / 3f + 0.5f),
                    (int)(height * 0.7273f - iconH / 3f + 0.5f),
                    (int)(width / 2f + iconW / 3f + 0.5f),
                    (int)(height * 0.7273f + iconH / 3f + 0.5f));
            icon.draw(canvas);
        }
    }

    public void setData(float maxValue, float curValue) {

        if(maxValue == 0f) return;

        if (curValue > maxValue) {
            curValue = maxValue;
        }

        value = String.format("%.1f", curValue);

        float scale = curValue / maxValue;
        float oldAngleLength = currentAngleLength;
        currentAngleLength = scale * angleLength;
        currentAngleLength = - currentAngleLength;

        /**开始执行动画*/
        setAnimation(oldAngleLength, currentAngleLength, animationLength);
    }

    public void setData(int maxValue, int curValue) {

        if(maxValue == 0) return;

        if (curValue > maxValue) {
            curValue = maxValue;
        }

        value = String.format("%d", curValue);

        float scale = (curValue + 0.f) / (maxValue + 0.f);
        float oldAngleLength = currentAngleLength;
        currentAngleLength = scale * angleLength;
        currentAngleLength = -currentAngleLength;

        /**开始执行动画*/
        setAnimation(oldAngleLength, currentAngleLength, animationLength);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void setAnimation(float old, float current, int length) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(old, current);
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

    public void setDrawable(int resId){
        icon = getResources().getDrawable(resId);
        postInvalidate();
    }

    public void setDrawable(Drawable drawable){
        icon = drawable;
        postInvalidate();
    }
}
