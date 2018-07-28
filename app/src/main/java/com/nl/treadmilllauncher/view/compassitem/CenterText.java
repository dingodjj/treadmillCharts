package com.nl.treadmilllauncher.view.compassitem;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.view.CompassScaleView;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class CenterText extends BaseItem {

    private Paint vTextPaint;
    private Paint linePaint;
    private int color = Color.parseColor("#64ddf2");
    private Resources res;

    private float distance = 0.0f;

    public CenterText(CompassScaleView compassScaleView) {
        super(compassScaleView);
        res = view.getResources();

        vTextPaint = new Paint();
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//抗锯齿功能
        vTextPaint.setColor(color);

        linePaint = new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setColor(color);
        linePaint.setStyle(Paint.Style.FILL);

        centerX = compassScaleView.getWidth() / 2;
        centerY = compassScaleView.getHeight() / 2;
    }

    @Override
    public void drawCanvas(Canvas canvas) {

        String value = "" + distance;

        Rect bounds = new Rect();
        vTextPaint.setTextSize(res.getDimension(R.dimen.x48));
        vTextPaint.getTextBounds(value, 0, value.length(), bounds);
        Paint.FontMetricsInt fontMetrics = vTextPaint.getFontMetricsInt();
        int baseline = (view.getHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(value, centerX, baseline - res.getDimension(R.dimen.x13), vTextPaint);
        canvas.save();

        vTextPaint.setTextSize(res.getDimension(R.dimen.x18));
        vTextPaint.getTextBounds(value, 0, value.length(), bounds);
        fontMetrics = vTextPaint.getFontMetricsInt();
        baseline = (view.getHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        vTextPaint.getTextBounds("公里", 0, 2, bounds);
        canvas.drawText("公里", centerX, baseline + res.getDimension(R.dimen.x33), vTextPaint);

        canvas.drawLine(centerX * 6 / 7, centerY + res.getDimension(R.dimen.x16),
                centerX * 8 / 7, centerY + res.getDimension(R.dimen.x16), linePaint);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
