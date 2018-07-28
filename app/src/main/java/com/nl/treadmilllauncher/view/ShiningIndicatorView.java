package com.nl.treadmilllauncher.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.utils.CommonUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dingo on 2018/3/5.
 */

public class ShiningIndicatorView extends View {

    private Paint textPaint, arcPaint;
    private int mShiningColor_hight = 0xFFE38B1E;
    private int mShiningColor_mid = 0x33FFB700;
    private int mShiningColor_low = 0x1AFFB700;
    private int[] mShiningColorInts = new int[3];
    private int text_direction = 0;
    private String description = "[未知]";
    private float textSize;

    private int FLAG = 0;
    private boolean isShining = false;
    private Rect rect;

    private final int SHINING_PERIOD = 0x0001;
    private Timer mShiningTimer;
    private Handler mHandler;

    public ShiningIndicatorView(Context context) {
        super(context);
    }

    public ShiningIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShiningIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = null;
        try {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShiningIndicatorView);
            text_direction = typedArray.getInt(R.styleable.ShiningIndicatorView_textDirection, 0);
            description = typedArray.getString(R.styleable.ShiningIndicatorView_description);
            textSize = typedArray.getFloat(R.styleable.ShiningIndicatorView_textSize, getResources().getDimension(R.dimen.x14));
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(0xffffffff);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        rect = new Rect();
        textPaint.getTextBounds(description, 0, description.length(), rect);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mHandler = new Handler(context.getMainLooper()){
            @Override
            public void dispatchMessage(Message msg) {
                if(msg.what == SHINING_PERIOD){
                    if(FLAG == 4){
                        FLAG = 0;
                    }else{
                        FLAG++;
                        changeColors(FLAG);
                    }
                }else {
                    super.dispatchMessage(msg);
                }
            }
        };

        mShiningTimer = new Timer(true);

        setShiningColor();
    }

    private void changeColors(int flag) {
        switch (flag){
            case 0:
            case 3:
                mShiningColorInts[0] = mShiningColor_hight;
                mShiningColorInts[1] = mShiningColor_mid;
                mShiningColorInts[2] = mShiningColor_low;
                break;
            case 1:
                mShiningColorInts[1] = mShiningColor_hight;
                mShiningColorInts[2] = mShiningColor_mid;
                mShiningColorInts[0] = mShiningColor_low;
                break;
            case 2:
                mShiningColorInts[2] = mShiningColor_hight;
                mShiningColorInts[0] = mShiningColor_mid;
                mShiningColorInts[1] = mShiningColor_low;
                break;
        }

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, (int)(width * 4f / 3f));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        float detaX = width / 10f;
        float detaX1 = detaX / 2f;
        float detaY = height / 4f;

        float cx = width / 2f;

        arcPaint.setStrokeWidth(detaX);

        if(text_direction == 0){

            float cy = detaY * 2.5f;

            //文字在上面
            if(isShining){
                RectF rectF = new RectF();

                arcPaint.setColor(mShiningColorInts[0]);
//                canvas.drawCircle(cx, cy, detaX, circlePaint);
                rectF.set(cx - detaX1, cy - detaX1, cx + detaX1, cy + detaX1);
                canvas.drawArc(rectF, 0, 360, false, arcPaint);

                arcPaint.setColor(mShiningColorInts[1]);
                rectF.set(cx - detaX - detaX1, cy - detaX - detaX1, cx + detaX + detaX1, cy + detaX + detaX1);
                canvas.drawArc(rectF, 0, 360, false, arcPaint);

                arcPaint.setColor(mShiningColorInts[2]);
                rectF.set(cx - 2 * detaX - detaX1, cy - 2 * detaX - detaX1, cx + 2 * detaX + detaX1, cy + 2 * detaX + detaX1);
                canvas.drawArc(rectF, 0, 360, false, arcPaint);

                canvas.drawText(description, cx, detaY / 2f + rect.height() / 2f, textPaint);
            }else{
                arcPaint.setColor(0xffffffff);
                canvas.drawCircle(cx, cy, detaX, arcPaint);

                canvas.drawText(description, cx, cy - detaY - rect.height() / 3f, textPaint);
            }
        }else{

            float cy = detaY * 1.5f;

            //文字在下面
            if(isShining){
                RectF rectF = new RectF();

                arcPaint.setColor(mShiningColorInts[0]);
//                canvas.drawCircle(cx, cy, detaX, circlePaint);
                rectF.set(cx - detaX1, cy - detaX1, cx + detaX1, cy + detaX1);
                canvas.drawArc(rectF, 0, 360, false, arcPaint);

                arcPaint.setColor(mShiningColorInts[1]);
                rectF.set(cx - detaX - detaX1, cy - detaX - detaX1, cx + detaX + detaX1, cy + detaX + detaX1);
                canvas.drawArc(rectF, 0, 360, false, arcPaint);

                arcPaint.setColor(mShiningColorInts[2]);
                rectF.set(cx - 2 * detaX - detaX1, cy - 2 * detaX - detaX1, cx + 2 * detaX + detaX1, cy + 2 * detaX + detaX1);
                canvas.drawArc(rectF, 0, 360, false, arcPaint);

                canvas.drawText(description, cx, height - rect.height() / 2f, textPaint);
            }else{
                arcPaint.setColor(0xffffffff);
                canvas.drawCircle(cx, cy, detaX, arcPaint);

                canvas.drawText(description, cx, cy + detaY + rect.height(), textPaint);
            }
        }
    }

    public void setShiningColor() {

        mShiningColorInts[0] = mShiningColor_hight;
        mShiningColorInts[1] = mShiningColor_mid;
        mShiningColorInts[2] = mShiningColor_low;

        FLAG = 0;
    }

    public void startShining(){
        isShining = true;
        mShiningTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(SHINING_PERIOD);
            }
        }, 0, 100);
    }

    public void stopShining(){

        isShining = false;

        mShiningTimer.cancel();

        mHandler.removeMessages(SHINING_PERIOD);
    }

//    @Override
//    protected void onAttachedToWindow() {
//        startShining();
//        super.onAttachedToWindow();
//    }

    @Override
    protected void onDetachedFromWindow() {
        stopShining();
        super.onDetachedFromWindow();
    }
}
