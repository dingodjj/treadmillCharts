package com.nl.treadmilllauncher.view;

/**
 * Created by yuandl on 2016-11-08.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nl.treadmilllauncher.R;

public class StepArcView extends View {

    private static final String TAG = "StepArcView";

    private String panel_title_en = "";
    private String panel_title_cn = "";
    private int start_color = Color.parseColor("#FF9400");
    private int end_color = Color.parseColor("#FF5B5B");
    private float max_progress = 10.f;
    private int direction = 0;
    private float current_progress = 0.f;
    private int subTextColor = Color.parseColor("#999999");
    private int mainTextColor = Color.parseColor("#FFFFFF");

    private float borderWidth = 15f;
    private float subTextSize = 20f;
    private float mainTextSize = 80f;
    private float startAngle = -90;
    private float angleLength = 360;
    private float currentAngleLength = 360;
    private int animationLength = 1000;

    private PointF point;
    private float centerX;

    private void init(AttributeSet attrs) {
        point = new PointF();

        if(attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StepArcView);

                panel_title_cn = typedArray.getString(R.styleable.StepArcView_panel_title_cn);
                panel_title_en = typedArray.getString(R.styleable.StepArcView_panel_title_en);
                max_progress = typedArray.getFloat(R.styleable.StepArcView_max_progress, 100.f);
                current_progress = typedArray.getFloat(R.styleable.StepArcView_current_progress, 0.f);
                direction = typedArray.getInt(R.styleable.StepArcView_direction, 0);
                start_color = typedArray.getColor(R.styleable.StepArcView_start_color, start_color);
                end_color = typedArray.getColor(R.styleable.StepArcView_end_color, end_color);
            } finally {
                if (typedArray != null)
                    typedArray.recycle();
            }
        }
        if(current_progress > max_progress || max_progress == 0){
            currentAngleLength = 360;
        }else {
            float scale = current_progress / max_progress;
            currentAngleLength = scale * angleLength;
        }

        if(direction == 1){
            currentAngleLength = -currentAngleLength;
        }

        mainTextSize = getResources().getDimension(R.dimen.x53);
        subTextSize = getResources().getDimension(R.dimen.x13);
        borderWidth = getResources().getDimension(R.dimen.x15);
    }

    public StepArcView(Context context) {
        super(context);
        init(null);
    }

    public StepArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StepArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**中心点的x坐标*/
        int width = getWidth();
        int height = getHeight();
        if(width >= height) {
            centerX = width / 2;
        }else{
            centerX = height / 2;
        }
        /**指定圆弧的外轮廓矩形区域*/
        RectF rectF = new RectF(0 + borderWidth, borderWidth, 2 * centerX - borderWidth, 2 * centerX - borderWidth);

        drawCurValue(canvas, centerX);
        drawOutRing(canvas, rectF);
        drawInnerArc(canvas, rectF);
        drawScale(canvas);

        drawText(canvas, centerX);
    }

    private void drawOutRing(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        /** 默认画笔颜色*/
        paint.setColor(0x4DFFFFFF);
        /** 结合处为圆弧*/
        paint.setStrokeJoin(Paint.Join.ROUND);
        /** 设置画笔的样式 Paint.Cap.Round ,Cap.SQUARE等分别为圆形、方形*/
        paint.setStrokeCap(Paint.Cap.ROUND);
        /** 设置画笔的填充样式 Paint.Style.FILL  :填充内部;Paint.Style.FILL_AND_STROKE  ：填充内部和描边;  Paint.Style.STROKE  ：仅描边*/
        paint.setStyle(Paint.Style.STROKE);
        /**抗锯齿功能*/
        paint.setAntiAlias(true);
        /**设置画笔宽度*/
        paint.setStrokeWidth(borderWidth);

        /**绘制圆弧的方法
         * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)//画弧，
         参数一是RectF对象，一个矩形区域椭圆形的界限用于定义在形状、大小、电弧，
         参数二是起始角(度)在电弧的开始，圆弧起始角度，单位为度。
         参数三圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
         参数四是如果这是true(真)的话,在绘制圆弧时将圆心包括在内，通常用来绘制扇形；如果它是false(假)这将是一个弧线,
         参数五是Paint对象；
         */
        canvas.drawArc(rectF, startAngle, angleLength, false, paint);

    }

    private void drawInnerArc(Canvas canvas, RectF rectF) {
        Paint paintCurrent = new Paint();
        paintCurrent.setStrokeJoin(Paint.Join.ROUND);
        paintCurrent.setStrokeCap(Paint.Cap.ROUND);//圆角弧度
        paintCurrent.setStyle(Paint.Style.STROKE);//设置填充样式
        paintCurrent.setAntiAlias(true);//抗锯齿功能
        paintCurrent.setStrokeWidth(borderWidth);//设置画笔宽度

        SweepGradient mSweepGradient = new SweepGradient(centerX, centerX, new int[]{ start_color,
                end_color}, null);
        //旋转渐变
        Matrix matrix = new Matrix();
        if(direction == 0) {
            matrix.setRotate(-95f, canvas.getWidth() / 2, canvas.getHeight() / 2);
        }else{
            matrix.setRotate(-85f, canvas.getWidth() / 2, canvas.getHeight() / 2);
        }
        mSweepGradient.setLocalMatrix(matrix);
        paintCurrent.setShader(mSweepGradient);
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, paintCurrent);
    }

    private void drawCurValue(Canvas canvas, float centerX) {
        String value = current_progress + "";
        Paint vTextPaint = new Paint();
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//抗锯齿功能
        vTextPaint.setTextSize(mainTextSize);
        vTextPaint.setColor(mainTextColor);
        Rect bounds_Number = new Rect();
        vTextPaint.getTextBounds(value, 0, value.length(), bounds_Number);
        Paint.FontMetricsInt fontMetrics = vTextPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(value, centerX, baseline, vTextPaint);
    }

    private void drawText(Canvas canvas, float centerX) {
        Paint vTextPaint = new Paint();
        vTextPaint.setTextSize(subTextSize);
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//抗锯齿功能
        vTextPaint.setColor(subTextColor);
        Rect bounds = new Rect();
        vTextPaint.getTextBounds(panel_title_en, 0, panel_title_en.length(), bounds);
        Paint.FontMetricsInt fontMetrics = vTextPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(panel_title_en, centerX, baseline + centerX / 2, vTextPaint);
        canvas.save();

        bounds = new Rect();
        vTextPaint.setTextSize(subTextSize * 5 / 3);
        fontMetrics = vTextPaint.getFontMetricsInt();
        baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        vTextPaint.getTextBounds(panel_title_cn, 0, panel_title_cn.length(), bounds);
        canvas.drawText(panel_title_cn, centerX, baseline - centerX / 2.5f, vTextPaint);
    }

    private void drawScale(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setStrokeWidth(1);
        mPaint.setColor(mainTextColor);
        /**要绘制的表盘线的总数**/
        int count = 27;
        /**要绘制的表盘每个间隔线条之间的夹角**/
        double avgAngle = (360d / (count - 1));
        /**要绘制的表盘的最长的半径**/
        float radius = centerX - borderWidth - getResources().getDimension(R.dimen.x12);
        /**要绘制的表盘线条长度**/
        float lineLength = getResources().getDimension(R.dimen.x7);
        /**起始点**/
        PointF point1 = new PointF();
        /**终止点**/
        PointF point2 = new PointF();
        for (int i = 0; i < count; i++) {
            double angle = avgAngle * i;
            /**起始点坐标**/
            point1.x = centerX + (float) Math.sin(angle * (Math.PI / 180)  + Math.PI) * radius;
            point1.y = centerX + (float) Math.cos(angle * (Math.PI / 180) + Math.PI) * radius;

            /**终止点坐标**/
            point2.x = centerX + (float) Math.sin(angle * (Math.PI / 180) + Math.PI) * (radius - lineLength);
            point2.y = centerX + (float) Math.cos(angle * (Math.PI / 180) + Math.PI) * (radius - lineLength);

            /**画线**/
            canvas.drawLine(point1.x, point1.y, point2.x, point2.y, mPaint);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void setCurrentCount(float max, float curValue) {
        this.max_progress = max;
        this.current_progress = curValue;

        if (curValue > max) {
            curValue = max;
        }

        float scale = curValue / max;
        float oldAngleLength = currentAngleLength;
        currentAngleLength = scale * angleLength;
        if(direction == 1){
            currentAngleLength = -currentAngleLength;
        }

        /**开始执行动画*/
        setAnimation(oldAngleLength, currentAngleLength, animationLength);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
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