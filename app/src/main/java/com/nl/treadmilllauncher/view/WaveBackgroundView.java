package com.nl.treadmilllauncher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by dingo on 2018/3/13.
 */

public class WaveBackgroundView extends View {

    private Paint mPaint;
    private Path mPath;

    private float firstAmplifier, secondAmplifier, thirdAmplifier;
    private float phase;
    private float offset = 0;

    private ValueAnimator animator;

    public WaveBackgroundView(Context context) {
        super(context);
    }

    public WaveBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WaveBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0x05ffffff);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        phase = width / 10f;

        firstAmplifier = height * 0.3f;
        secondAmplifier = height * 0.3f;
        thirdAmplifier = height * 0.12f;

        setPathToDraw(firstAmplifier,height / 4f, height);
        canvas.drawPath(mPath, mPaint);

        setPathToDrawX(secondAmplifier,height / 2f, height);
        canvas.drawPath(mPath, mPaint);

        setPathToDraw(thirdAmplifier,height * 5 / 6f, height);
        canvas.drawPath(mPath, mPaint);
    }

    private void setPathToDraw(float amplifier, float baseline, float height){

        float startX = - 4 * phase + offset;

        mPath.reset();
        mPath.moveTo(startX, baseline);
        mPath.quadTo(phase + startX, baseline - amplifier, 2 * phase + startX, baseline);
        mPath.quadTo(3 * phase + startX, baseline + amplifier, 4 * phase + startX, baseline);
        mPath.quadTo(5 * phase + startX, baseline - amplifier, 6 * phase + startX, baseline);
        mPath.quadTo(7 * phase + startX, baseline + amplifier, 8 * phase + startX, baseline);
        mPath.quadTo(9 * phase + startX, baseline - amplifier, 10 * phase + startX, baseline);
        mPath.quadTo(11 * phase + startX, baseline + amplifier, 12 * phase + startX, baseline);
        mPath.quadTo(13 * phase + startX, baseline - amplifier, 14 * phase + startX, baseline);
        mPath.quadTo(15 * phase + startX, baseline + amplifier, 16 * phase + startX, baseline);
        mPath.lineTo(16 * phase + startX, height);
        mPath.lineTo(startX, height);
        mPath.close();
    }

    private void setPathToDrawX(float amplifier, float baseline, float height){

        float startX = - 8 * phase + 2 * offset;

        mPath.reset();
        mPath.moveTo(startX, baseline);
        mPath.quadTo(phase + startX, baseline - amplifier, 2 * phase + startX, baseline);
        mPath.quadTo(3 * phase + startX, baseline + amplifier, 4 * phase + startX, baseline);
        mPath.quadTo(5 * phase + startX, baseline - amplifier, 6 * phase + startX, baseline);
        mPath.quadTo(7 * phase + startX, baseline + amplifier, 8 * phase + startX, baseline);
        mPath.quadTo(9 * phase + startX, baseline - amplifier, 10 * phase + startX, baseline);
        mPath.quadTo(11 * phase + startX, baseline + amplifier, 12 * phase + startX, baseline);
        mPath.quadTo(13 * phase + startX, baseline - amplifier, 14 * phase + startX, baseline);
        mPath.quadTo(15 * phase + startX, baseline + amplifier, 16 * phase + startX, baseline);
        mPath.quadTo(17 * phase + startX, baseline - amplifier, 18 * phase + startX, baseline);
        mPath.quadTo(19 * phase + startX, baseline + amplifier, 20 * phase + startX, baseline);
        mPath.lineTo(20 * phase + startX, height);
        mPath.lineTo(startX, height);
        mPath.close();
    }

    public void startMove(){
        if(animator != null && animator.isRunning()){
            return;
        }

        if(animator == null) {
            animator = ValueAnimator.ofFloat(0f, getWidth() * 2f / 5f);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offset = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setDuration(2000);
        }
        animator.start();
    }

    public void stopMove(){
        if(animator != null && animator.isRunning()){
            animator.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
//        startMove();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMove();
        super.onDetachedFromWindow();
    }
}
