package com.nl.treadmilllauncher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by DengXiao on 2017/4/7.
 */

public class RTextView extends AppCompatTextView {

    private Rect mTextBound = new Rect();
    private LinearGradient secondGradient, thirdGradient;

    public RTextView(Context context) {
        super(context);
    }

    public RTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setSecondGradient() {
        if(secondGradient == null) {
            secondGradient = new LinearGradient(0, 0, getWidth(), getHeight(),
                    0xff395369, 0xffeeeeee, Shader.TileMode.CLAMP);
        }
        getPaint().setShader(secondGradient);
        postInvalidate();
    }

    public void setThirdGradient(){
        if(thirdGradient == null){
            thirdGradient = new LinearGradient(0, 0, getWidth(), getHeight(),
                    0x00ffffff, 0x0fffffff, Shader.TileMode.CLAMP);
        }
        getPaint().setShader(secondGradient);
        postInvalidate();
    }

    public void setNoGradient(){
        getPaint().setShader(null);
        getPaint().setColor(0xffffffff);
        postInvalidate();
    }
}