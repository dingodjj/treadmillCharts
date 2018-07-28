package com.nl.treadmilllauncher.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nl.treadmilllauncher.R;

/**
 * Created by dingo on 2018/3/17.
 */

public class GradientSeparatorLine extends View {

    private int line_color = 0xffffffff;
    private int orientation = 0;
    private Paint mPaint;

    public GradientSeparatorLine(Context context) {
        super(context);
    }

    public GradientSeparatorLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GradientSeparatorLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = null;
        try {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GradientSeparatorLine);
            line_color = typedArray.getColor(R.styleable.GradientSeparatorLine_lineColor, line_color);
            orientation = typedArray.getInt(R.styleable.GradientSeparatorLine_orientation, 0);
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        if(mPaint.getShader() == null){
            int[] colorArr = new int[3];
            colorArr[0] = 0x00ffffff & line_color;
            colorArr[1] = line_color;
            colorArr[2] = 0x00ffffff & line_color;

            LinearGradient gradient = new LinearGradient(0, 0, width, height,
                    colorArr, new float[]{0f, 0.5f, 1}, Shader.TileMode.MIRROR);
            Matrix matrix = new Matrix();
            if(orientation == 1 ){
                matrix.setRotate(90f);
            }
            gradient.getLocalMatrix(matrix);
            mPaint.setShader(gradient);
        }

        if(orientation == 0) {
            mPaint.setStrokeWidth(height);
        }else {
            mPaint.setStrokeWidth(width);
        }

        canvas.drawLine(0, 0, width, height, mPaint);
    }
}
