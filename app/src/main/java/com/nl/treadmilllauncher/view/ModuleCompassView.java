package com.nl.treadmilllauncher.view;

import android.animation.Animator;
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

public class ModuleCompassView extends View {

    public static final String TAG = "ModuleCompassView";

    private int[] pointColors = new int[7];
    private int darkColor = Color.parseColor("#1affffff");
    private Drawable bgDrawable;

    private float total_km = 0.0f;
    private float cur_km = 0.0f;

    private float textSize;
    private Paint colorPaint, darkPaint, textPaint;
    private RectF rectF;
    private int mainTextColor = Color.parseColor("#FFFFFF");

    private float centerX = 0f;
    private float centerY = 0f;
    int parent_width = 0;
    int parent_height = 0;
    private float radius = 0;

    private float[] dataSource = { 0f, 0f, 0f, 0f, 0f, 0f};
    private float[] limitDataSource = {0f, 0f, 0f, 0f, 0f, 0f};
    private ValueAnimator flashAnimator;
    private int currentIndex = -1;

    public ModuleCompassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ModuleCompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.ModuleCompassView);
            textSize = typedArray.getFloat(R.styleable.ModuleCompassView_textSize, getResources().getDimension(R.dimen.x66));
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        bgDrawable = context.getResources().getDrawable(R.mipmap.run_compass_bg);

        pointColors[0] = Color.parseColor("#b7fb40");
        pointColors[1] = Color.parseColor("#2fc5c0");
        pointColors[2] = Color.parseColor("#09fcdd");
        pointColors[3] = Color.parseColor("#d2d928");
        pointColors[4] = Color.parseColor("#5bda98");
        pointColors[5] = Color.parseColor("#ad880b");
        pointColors[6] = Color.parseColor("#b7fb40");

        colorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorPaint.setStyle(Paint.Style.STROKE);
        SweepGradient shader = new SweepGradient(0, 0, pointColors, null);
        Matrix matrix = new Matrix();
        shader.getLocalMatrix(matrix);
        matrix.setRotate(-90);
        shader.setLocalMatrix(matrix);
        colorPaint.setShader(shader);

        darkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        darkPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(mainTextColor);
        textPaint.setTypeface(CommonUtils.getTfByPath(getContext(), "fonts/MyriadPro-BoldCond.otf"));
        textPaint.setTextSize(textSize);

        rectF = new RectF();
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
        radius = parent_width > parent_height ? parent_height / 2f : parent_width / 2f;

        drawBackground(canvas);
        drawText(canvas);
        drawSector(canvas);
    }

    private void drawSector(Canvas canvas) {

        float[] widthRatio = {0.45f, 0.54f, 0.63f, 0.72f};
        float[] ratio = {0.25f, 0.5f, 0.75f, 1f};
        float strokeWidth = parent_height * 0.035f;

        for (int i = 0; i < 4; i++) {
            canvas.save();
            canvas.translate(centerX, centerY);

            rectF.set(-radius * widthRatio[i], -radius * widthRatio[i],
                    radius * widthRatio[i], radius * widthRatio[i]);

            colorPaint.setStrokeWidth(strokeWidth);
            darkPaint.setStrokeWidth(strokeWidth);

            for (int j = 0; j < 6; j++) {
                if(dataSource[j] >= limitDataSource[j] * ratio[i]){
                    canvas.drawArc(rectF, -89 + 60 * j, 58, false, colorPaint);
                }else{
                    darkPaint.setColor(darkColor);
                    canvas.drawArc(rectF, -89 + 60 * j, 58, false, darkPaint);
                }
            }
            canvas.restore();
        }
    }

    private void drawText(Canvas canvas) {
        String value = String.format("%.1f", cur_km);
        Rect rect = new Rect();
        textPaint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, centerX, centerY + rect.height() / 5f , textPaint);
    }

    private void drawBackground(Canvas canvas) {
        bgDrawable.setBounds(0,0,parent_width, parent_height);
        bgDrawable.draw(canvas);
    }

    public void setDataSource(float[] limitDataSource, float[] dataSource) {
        if(dataSource == null) return;
        this.limitDataSource = limitDataSource;
        this.dataSource = dataSource;
        postInvalidate();
    }

    public void stopAnimator(){
        if(flashAnimator != null){
            flashAnimator.cancel();
            flashAnimator = null;
        }
        currentIndex = -1;
    }

    public void startAnimator(final int valueIndex){
        if(valueIndex < 0 || valueIndex > 5 || valueIndex == currentIndex) return;
        currentIndex = valueIndex;
        final float backupValue = dataSource[valueIndex];

        flashAnimator = ValueAnimator.ofFloat(0.0f, dataSource[valueIndex]);
        flashAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dataSource[currentIndex] = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        flashAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dataSource[valueIndex] = backupValue;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        flashAnimator.setDuration(4000);
        flashAnimator.setRepeatCount(ValueAnimator.INFINITE);
        flashAnimator.start();
    }
}
