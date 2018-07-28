package com.nl.treadmilllauncher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.utils.CommonUtils;

/**
 * Created by Administrator on 2018/2/4.
 */

public class GoalModuleCompassView extends View {

    public static final String TAG = "ModuleCompassView";

    private Drawable bgDrawable;

    private float total_km = 3.0f;
    private float cur_km = 1.0f;

    private float textSize;
    private Paint arcPaint, textPaint;
    private float startAngle = -90;
    private float angleLength = 360;
    private float currentAngleLength = 360;
    private int animationLength = 1000;

    private float centerX = 0f;
    private float centerY = 0f;
    int parent_width = 0;
    int parent_height = 0;
    private RectF rectF;

    public GoalModuleCompassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GoalModuleCompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){

        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.GoalModuleCompassView);
            textSize = typedArray.getFloat(R.styleable.GoalModuleCompassView_textSize, getResources().getDimension(R.dimen.x66));
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        bgDrawable = context.getResources().getDrawable(R.mipmap.goal_compass_bg);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);//圆角弧度
        arcPaint.setStyle(Paint.Style.STROKE);//设置填充样式

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTypeface(CommonUtils.getTfByPath(getContext(), "fonts/MyriadPro-BoldCond.otf"));
        textPaint.setTextSize(textSize);

        rectF = new RectF();

        currentAngleLength = cur_km * angleLength / total_km;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(height, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        parent_width = getMeasuredWidth();
        parent_height = getMeasuredHeight();

        centerX = parent_width / 2;
        centerY = parent_height / 2;

        drawBackground(canvas);
        drawText(canvas);
        drawArc(canvas);
    }

    private void drawArc(Canvas canvas) {
        float width = parent_height * 0.03f;
        arcPaint.setStrokeWidth(width);
        if(arcPaint.getShader() == null){
            SweepGradient shader = new SweepGradient(centerX, centerY, new int[] { 0xFF34EAAB, 0xFF1D717B }, null);
            Matrix matrix = new Matrix();
            shader.getLocalMatrix(matrix);
            matrix.setRotate(-95f);
            shader.setLocalMatrix(matrix);
            arcPaint.setShader(shader);
        }

        rectF.set(centerX * 0.5f + width / 2.5f, centerY * 0.5f + width / 2.5f,
                centerX * 1.5f - width / 2.5f, centerY * 1.5f - width / 2.5f);
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, arcPaint);
    }

    private void drawText(Canvas canvas) {
        String value = String.format("%.1f", cur_km);
        Rect rect = new Rect();
        textPaint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, centerX, centerY + rect.height() / 4f, textPaint);
    }

    private void drawBackground(Canvas canvas) {
        bgDrawable.setBounds(0,0,parent_width, parent_height);
        bgDrawable.draw(canvas);
    }

    public void setCurrentCount(float max, float curValue) {
        if (curValue > max) {
            curValue = max;
        }

        this.total_km = max;
        this.cur_km = curValue;

        float scale = curValue / max;
        float oldAngleLength = currentAngleLength;
        currentAngleLength = scale * angleLength;

        /**开始执行动画*/
        setAnimation(oldAngleLength, currentAngleLength, animationLength);
    }

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
}
