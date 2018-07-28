package com.nl.treadmilllauncher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.utils.CommonUtils;

/**
 * Created by dingo on 2018/3/6.
 */

public class RunInfoAnimPanelView extends View {

    private Drawable animBg, bg;
    private Paint textPaint;

    private int type = 0;

    private float value = 10;

    private ValueAnimator animator;
    private float animatorValue = 0f;

    public RunInfoAnimPanelView(Context context) {
        super(context);
    }

    public RunInfoAnimPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RunInfoAnimPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        TypedArray typedArray = null;
        try {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RunInfoAnimPanelView);
            type = typedArray.getInt(R.styleable.RunInfoAnimPanelView_type, 0);
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xffffffff);
        textPaint.setTextSize(getResources().getDimension(R.dimen.x34));
        textPaint.setTypeface(CommonUtils.getTfByPath(getContext(), "fonts/MyriadPro-BoldCond.otf"));
        textPaint.setTextAlign(Paint.Align.CENTER);

        animBg = getResources().getDrawable(R.mipmap.anim_border_bg);
        start();

        if(type == 0){
            bg = getResources().getDrawable(R.mipmap.kcal_anim_bg);
        }else{
            bg = getResources().getDrawable(R.mipmap.speed_anim_bg);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        canvas.save();
        canvas.rotate(animatorValue, width / 2f, height / 2f);
        animBg.setBounds(0, 0, width, height);
        animBg.draw(canvas);
        canvas.restore();

        bg.setBounds((int)(width * 0.05f), (int)(height * 0.05f), (int)(width * 0.95f), (int)(height * 0.95f));
        bg.draw(canvas);

        String value_text = "";
        if(type == 0){
            value_text = String.valueOf((int)value);
        }else{
            value_text = String.format("%.1f", value);
        }

        Rect rect = new Rect();
        textPaint.getTextBounds(value_text, 0, value_text.length(), rect);
        canvas.drawText(value_text, width / 2f, height / 2f  + rect.height() / 4f , textPaint);

        super.onDraw(canvas);
    }

    private void start() {
        if (animator != null && animator.isRunning()) {
            return;
        }

        if(animator == null) {
            animator = ValueAnimator.ofFloat(0, 360);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatorValue = (Float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animator.setDuration(5000);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
        }
        animator.start();
    }

    private void stop(){
        if(animator != null && animator.isRunning()){
            animator.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        start();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }
}
