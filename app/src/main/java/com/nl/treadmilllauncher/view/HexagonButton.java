package com.nl.treadmilllauncher.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dingo on 2018/3/14.
 */

public class HexagonButton extends AppCompatButton {

    public static final String TAG = "HexagonButton";

    private int width = -1, height = -1;
    private Bitmap bitmap;

    public HexagonButton(Context context) {
        super(context);
    }

    public HexagonButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HexagonButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent( event);
        }
        int x = (int)event.getX();
        int y = (int)event.getY();
        if(width == -1 || height == -1) {
            Drawable drawable = ((StateListDrawable)getBackground()).getCurrent();
            bitmap = ((BitmapDrawable)drawable).getBitmap();
            width = getWidth();
            height = getHeight();
        }

        if(null == bitmap || x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        try {
            int pixel = bitmap.getPixel(x, y);
            if (Color.TRANSPARENT == pixel) {
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return super.onTouchEvent( event);
    }

}
