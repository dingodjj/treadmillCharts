package com.nl.treadmilllauncher.view.compassitem;

import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.nl.treadmilllauncher.view.CompassScaleView;

public class Ring extends BaseItem {

	private RectF tempRectF;

	public Ring(CompassScaleView compassScaleView) {
		super(compassScaleView);
		tempRectF = new RectF();
		paint.setStyle(Style.STROKE);
	}

	public float ringWidth;

	public float ringAngle;

	public float startAngle;

	public float offsetAngle;

	public float ringRadius;

	public float ringSpeed;

	public int ringColor;

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setColor(ringColor);
		paint.setStrokeWidth(ringWidth);
		tempRectF.set(getPxByWeight(centerX - ringRadius), getPxByWeight(centerY - ringRadius),
				getPxByWeight(centerX + ringRadius), getPxByWeight(centerY + ringRadius));
		canvas.drawArc(tempRectF, startAngle + offsetAngle, ringAngle, false, paint);
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

	public float getRingAngle() {
		return ringAngle;
	}

	public void setRingAngle(float ringAngle) {
		this.ringAngle = ringAngle;
	}

	public float getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(float startAngle) {
		this.startAngle = startAngle;
	}

	public float getOffsetAngle() {
		return offsetAngle;
	}

	public void setOffsetAngle(float offsetAngle) {
		this.offsetAngle = offsetAngle;
	}

	public float getRingRadius() {
		return ringRadius;
	}

	public void setRingRadius(float ringRadius) {
		this.ringRadius = ringRadius;
	}

	public float getRingSpeed() {
		return ringSpeed;
	}

	public void setRingSpeed(float ringSpeed) {
		this.ringSpeed = ringSpeed;
	}

	public int getRingColor() {
		return ringColor;
	}

	public void setRingColor(int ringColor) {
		this.ringColor = ringColor;
	}
	
}