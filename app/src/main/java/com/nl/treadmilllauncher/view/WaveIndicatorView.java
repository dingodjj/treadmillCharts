package com.nl.treadmilllauncher.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nl.treadmilllauncher.R;

/**
 * Created by dingo on 2018/3/5.
 */

public class WaveIndicatorView extends ViewGroup {

    private Path mPath;
    private Paint mPaint;

    private int lineColor = 0xffffffff;

    public WaveIndicatorView(Context context) {
        super(context);
    }

    public WaveIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public WaveIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();

        if(count > 0) {

            int width = getWidth();
            int height = getHeight();

            float φ = width / (count + 1.0f);
            float[] gapHeights = { -0.285f, -0.069f, 0.16f, 0.25f, -0.071f, -0.191f};

            View child;
            for (int i = 0; i < count; i++) {
                child = getChildAt(i);

                float cx = child.getMeasuredWidth() / 2;
                float cy = child.getMeasuredHeight() / 2;

                child.layout((int)(φ * (i + 1) - cx + 0.5f), (int)(height / 2f - cy + height * gapHeights[i] + 0.5f),
                        (int)(φ * (i + 1) + cx+ 0.5f), (int)(height / 2f + height * gapHeights[i] + cy+ 0.5f));
            }
        }
    }

    private void init(@Nullable AttributeSet attrs){

        TypedArray typedArray = null;
        try {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WaveIndicatorView);
            lineColor = typedArray.getColor(R.styleable.WaveIndicatorView_lineColor, 0xffffffff);
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        float φ = width / 6f;

        if(mPaint.getShader() == null) {

            int[] colorArr = new int[3];
            colorArr[0] = 0x00ffffff & lineColor;
            colorArr[1] = lineColor;
            colorArr[2] = 0x00ffffff & lineColor;

            LinearGradient gradient = new LinearGradient(0, height / 2f, width, height / 2f,
                    colorArr, new float[]{0f, 0.5f, 1}, Shader.TileMode.MIRROR);
            mPaint.setShader(gradient);
        }

        mPath.reset();
        mPath.moveTo(0, height / 2f);

        mPath.quadTo(φ, 0, 2 * φ, height / 2f);
        mPath.quadTo(3 * φ, height, 4 * φ, height / 2f);
        mPath.quadTo(5 * φ, 0, 6 * φ, height / 2f);

        canvas.drawPath(mPath, mPaint);
    }
}
