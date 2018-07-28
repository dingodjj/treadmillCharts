package com.nl.treadmilllauncher.view;

/**
 * Created by yuandl on 2016-11-08.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.utils.CommonUtils;

public class QuarterView extends View {

    private static final String TAG = "QuarterView";

    private String text_en = "";
    private String text_cn = "";
    private int blur_color = Color.parseColor("#02a3eb");
    private float total_progress = 10.f;
    private float current_progress = 0.f;
    private int position = 0;
    private int subTextColor = Color.parseColor("#33FFFFFF");
    private int mainTextColor = Color.parseColor("#FFFFFF");
    private Drawable bgDrawable;

    private float textSize = 20f;
    private float currentAngleLength = 90;
    private float angleLength = 90;
    private int animationLength = 1000;

    private float centerX;
    private float centerY;
    private int radius;
    private int startAngle = 0;

    private void init(AttributeSet attrs) {
        if(attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QuarterView);

                text_cn = typedArray.getString(R.styleable.QuarterView_text_cn);
                text_en = typedArray.getString(R.styleable.QuarterView_text_en);
                total_progress = typedArray.getFloat(R.styleable.QuarterView_total_progress, 100.f);
                current_progress = typedArray.getFloat(R.styleable.QuarterView_cur_progress, 0.f);
                position = typedArray.getInt(R.styleable.QuarterView_position, 0);
                blur_color = typedArray.getColor(R.styleable.QuarterView_blur_color, blur_color);
            } finally {
                if (typedArray != null)
                    typedArray.recycle();
            }
        }

        if(current_progress > total_progress || total_progress == 0){
            currentAngleLength = 90;
        }else {
            float scale = current_progress / total_progress;
            currentAngleLength = scale * angleLength;
        }

        if(position == 0){
            bgDrawable = getContext().getResources().getDrawable(R.mipmap.right_up_bg);
        }else if(position == 1){
            bgDrawable = getContext().getResources().getDrawable(R.mipmap.left_up_bg);
        }
        textSize = CommonUtils.sp2px(getContext(), 20);
    }

    public QuarterView(Context context) {
        super(context);
        init(null);
    }

    public QuarterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public QuarterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        radius = getMeasuredWidth();

        switch (position){
            case 0:
                //右上
                centerX = 0;
                centerY = radius;
                startAngle = 270;
                break;
            case 1:
                //左上
                centerX = radius;
                centerY = radius;
                startAngle = 180;
                break;
        }

        drawOutArc(canvas);
        drawText(canvas);
        drawScale(canvas);
    }

    private void drawOutArc(Canvas canvas) {
        bgDrawable.setBounds(0, 0, radius, radius);
        bgDrawable.draw(canvas);
    }

    private void drawText(Canvas canvas) {
        Paint vTextPaint = new Paint();
        vTextPaint.setTextSize(textSize * 33f / 25f);
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//抗锯齿功能
        vTextPaint.setColor(subTextColor);
        Rect bounds = new Rect();
        vTextPaint.getTextBounds(text_cn, 0, text_cn.length(), bounds);
        if(position == 0) {
            canvas.drawText(text_cn, radius * 0.31f, centerY - radius * 0.5f - bounds.height() / 2f, vTextPaint);
        }else if(position == 1){
            canvas.drawText(text_cn, centerX - radius * 0.31f, centerY - radius * 0.5f - bounds.height() / 2f, vTextPaint);
        }
        canvas.save();

        vTextPaint.setTextSize(textSize);
        vTextPaint.getTextBounds(text_en, 0, text_en.length(), bounds);
        if(position == 0) {
            canvas.drawText(text_en, radius * 0.31f, centerY - radius * 0.06f - bounds.height() / 2f, vTextPaint);
        }else if(position == 1){
            canvas.drawText(text_en, centerX - radius * 0.31f, centerY - radius * 0.06f - bounds.height() / 2f, vTextPaint);
        }
        canvas.save();

        String value = current_progress + "";
        vTextPaint.setTextSize(textSize * 67f / 30f);
        vTextPaint.setColor(mainTextColor);
        vTextPaint.setTypeface(CommonUtils.getTfByPath(getContext(), "fonts/MSTIFFHEIPRC-ULTRABOLD.TTF"));
        vTextPaint.getTextBounds(value, 0, value.length(), bounds);
        if(position == 0) {
            canvas.drawText(value, radius * 0.31f, centerY - radius * 0.2f - bounds.height() / 2f, vTextPaint);
        }else if(position == 1){
            canvas.drawText(value, centerX - radius * 0.31f, centerY - radius * 0.2f - bounds.height() / 2f, vTextPaint);
        }
    }

    private void drawScale(Canvas canvas) {
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        float gap = radius * 0.06f;

        for (int i = 2; i < 90; i += 2) {
            canvas.save();
            canvas.translate(centerX, centerY);
            if(position == 0) {
                canvas.rotate(i, 0, 0);
            }else if(position == 1){
                canvas.rotate(-i, 0, 0);
            }

            if(i < 90 - currentAngleLength){
                mPaint.setColor(subTextColor);
            }else{
                mPaint.setColor(mainTextColor);
            }
            canvas.drawLine(0, -radius + gap, 0, -radius + 2 * gap, mPaint);
            canvas.restore();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void setCurProgress(float max, float curValue) {
        this.total_progress = max;
        this.current_progress = curValue;
        float oldAngleLength = currentAngleLength;
        /**如果当前走的步数超过总步数则圆弧还是270度，不能成为园*/
        if (curValue > max || max == 0) {
            currentAngleLength = 90;
        }else{
            float scale = curValue / max;
            currentAngleLength = scale * angleLength;
        }

        /**开始执行动画*/
        setAnimation(oldAngleLength, currentAngleLength, animationLength);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
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