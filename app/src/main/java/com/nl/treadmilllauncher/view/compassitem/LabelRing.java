package com.nl.treadmilllauncher.view.compassitem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.TypedValue;

import com.nl.treadmilllauncher.view.CompassScaleView;

public class LabelRing extends BaseItem {

	private TextPaint tp;
	private Path mPath;

	public String labelText;

	public int labelColor;

	public int labelIcon;

	public float labelRadius;

	public float labelWidth;

	public float labelAngle;

	public float labelCenterAngle;

	private RectF tempRectF;

	public LabelRing(CompassScaleView compassScaleView) {
		super(compassScaleView);
		tempRectF = new RectF();
		mPath = new Path();
		tp = new TextPaint();
		tp.setColor(Color.WHITE);
		tp.setAntiAlias(true);
		tp.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14,
				compassScaleView.getContext().getResources().getDisplayMetrics()));
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setStyle(Style.STROKE);
		paint.setStrokeCap(Paint.Cap.SQUARE);
		paint.setColor(labelColor);
		paint.setStrokeWidth(getPxByWeight(labelWidth));
		tempRectF.set(getPxByWeight(centerX - labelRadius), getPxByWeight(centerY - labelRadius),
				getPxByWeight(centerX + labelRadius), getPxByWeight(centerY + labelRadius));
		canvas.drawArc(tempRectF, labelCenterAngle - 90 - labelAngle / 2, labelAngle, false, paint);

//		canvas.save();
//		canvas.rotate(labelCenterAngle, getPxByWeight(centerX), getPxByWeight(centerY));
//		canvas.restore();
//
//		float textLength = tp.measureText(labelText);
//		float textHOffset = getPxByWeight((float) (labelRadius * Math.sin(Math.toRadians(labelAngle / 2))))
//				- textLength / 2;
//		float textVOffset = tp.getTextSize() / 3;
//
//		mPath.rewind();
//		if (labelCenterAngle > 90 && labelCenterAngle < 270) {
//			mPath.addArc(tempRectF, labelCenterAngle - 90 + labelAngle / 2, -labelAngle);
//		} else {
//			mPath.addArc(tempRectF, labelCenterAngle - 90 - labelAngle / 2, labelAngle);
//		}
//		canvas.drawTextOnPath(labelText, mPath, textHOffset, textVOffset, tp);
	}

	public TextPaint getTp() {
		return tp;
	}

	public void setTp(TextPaint tp) {
		this.tp = tp;
	}

	public Path getmPath() {
		return mPath;
	}

	public void setmPath(Path mPath) {
		this.mPath = mPath;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	public int getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(int labelColor) {
		this.labelColor = labelColor;
	}

	public int getLabelIcon() {
		return labelIcon;
	}

	public void setLabelIcon(int labelIcon) {
		this.labelIcon = labelIcon;
	}

	public float getLabelRadius() {
		return labelRadius;
	}

	public void setLabelRadius(float labelRadius) {
		this.labelRadius = labelRadius;
	}

	public float getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(float labelWidth) {
		this.labelWidth = labelWidth;
	}

	public float getLabelAngle() {
		return labelAngle;
	}

	public void setLabelAngle(float labelAngle) {
		this.labelAngle = labelAngle;
	}

	public float getLabelCenterAngle() {
		return labelCenterAngle;
	}

	public void setLabelCenterAngle(float labelCenterAngle) {
		this.labelCenterAngle = labelCenterAngle;
	}

	public RectF getTempRectF() {
		return tempRectF;
	}

	public void setTempRectF(RectF tempRectF) {
		this.tempRectF = tempRectF;
	}

}
