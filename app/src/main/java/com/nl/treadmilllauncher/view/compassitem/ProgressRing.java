package com.nl.treadmilllauncher.view.compassitem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.nl.treadmilllauncher.view.CompassScaleView;

public class ProgressRing extends BaseItem {

	private RectF tempRectF;

	public ProgressRing(CompassScaleView compassScaleView) {
		super(compassScaleView);
		tempRectF = new RectF();
		paint.setStyle(Style.FILL);
	}

	public float ringWidth;

	public int ringColor;

	public float ringRadius;

	public int progressStartColor;
	public int progressEndColor;

	public int ringCount;

	public int progressStartCount;

	public int progressCount;

	public int getColor(int currentProgress) {
		int startAlpha = Color.alpha(progressStartColor);
		int startRed = Color.red(progressStartColor);
		int startGreen = Color.green(progressStartColor);
		int startBlue = Color.blue(progressStartColor);

		int endAlpha = Color.alpha(progressEndColor);
		int endRed = Color.red(progressEndColor);
		int endGreen = Color.green(progressEndColor);
		int endBlue = Color.blue(progressEndColor);

		int alpha = startAlpha + (endAlpha - startAlpha) * currentProgress / progressCount;
		int red = startRed + (endRed - startRed) * currentProgress / progressCount;
		int green = startGreen + (endGreen - startGreen) * currentProgress / progressCount;
		int blue = startBlue + (endBlue - startBlue) * currentProgress / progressCount;

		return Color.argb(alpha, red, green, blue);
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		for (int i = 0; i < ringCount; i++) {
			paint.setColor(ringColor);
			if (i >= progressStartCount && i < progressCount + progressStartCount) {
				paint.setColor(getColor(i - progressStartCount));
			}
			canvas.save();
			canvas.translate(getPxByWeight(centerX), getPxByWeight(centerY));
			canvas.rotate(i * 360.0f / (ringCount), 0, 0);
			tempRectF.set(-2, getPxByWeight(ringRadius - ringWidth / 2), 2, getPxByWeight(ringRadius + ringWidth / 2));
			canvas.drawRect(tempRectF, paint);
			canvas.restore();
		}
	}

	public RectF getTempRectF() {
		return tempRectF;
	}

	public void setTempRectF(RectF tempRectF) {
		this.tempRectF = tempRectF;
	}

	public float getRingWidth() {
		return ringWidth;
	}

	public void setRingWidth(float ringWidth) {
		this.ringWidth = ringWidth;
	}

	public int getRingColor() {
		return ringColor;
	}

	public void setRingColor(int ringColor) {
		this.ringColor = ringColor;
	}

	public float getRingRadius() {
		return ringRadius;
	}

	public void setRingRadius(float ringRadius) {
		this.ringRadius = ringRadius;
	}

	public int getProgressStartColor() {
		return progressStartColor;
	}

	public void setProgressStartColor(int progressStartColor) {
		this.progressStartColor = progressStartColor;
	}

	public int getProgressEndColor() {
		return progressEndColor;
	}

	public void setProgressEndColor(int progressEndColor) {
		this.progressEndColor = progressEndColor;
	}

	public int getRingCount() {
		return ringCount;
	}

	public void setRingCount(int ringCount) {
		this.ringCount = ringCount;
	}

	public int getProgressStartCount() {
		return progressStartCount;
	}

	public void setProgressStartCount(int progressStartCount) {
		this.progressStartCount = progressStartCount;
	}

	public int getProgressCount() {
		return progressCount;
	}

	public void setProgressCount(int progressCount) {
		this.progressCount = progressCount;
	}

}