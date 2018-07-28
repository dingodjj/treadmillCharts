package com.nl.treadmilllauncher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.nl.treadmilllauncher.R;

/**
 * Created by dingo on 2018/3/19.
 */

public class TextProgressbar extends ProgressBar {

    private Paint textPaint;
    private int mRealWidth;
    private Drawable progressBg;

    public TextProgressbar(Context context) {
        super(context);
    }

    public TextProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xffffffff);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(getResources().getDimension(R.dimen.x10));

        progressBg = getResources().getDrawable(R.drawable.progress_bar_bg);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mRealWidth = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        float radio = getProgress() * 1.0f / getMax();
        float progressPosX = (int) (mRealWidth * radio);
        String text = getProgress() + "%";

        if(progressBg != null){
            progressBg.setBounds(0, 0, (int)(progressPosX + 0.5f), getHeight());
            progressBg.draw(canvas);
        }

        float textWidth = textPaint.measureText(text);
        float textHeight = (textPaint.descent() + textPaint.ascent()) / 2;

        canvas.drawText(text, progressPosX - textWidth / 2f - 5, getHeight() / 2f - textHeight / 2, textPaint);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);

        ValueAnimator progressAnimator = ValueAnimator.ofInt(getMax(), progress);
        progressAnimator.setDuration(1000);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                TextProgressbar.super.setProgress((int) animation.getAnimatedValue());
            }
        });
        progressAnimator.start();
    }
}
