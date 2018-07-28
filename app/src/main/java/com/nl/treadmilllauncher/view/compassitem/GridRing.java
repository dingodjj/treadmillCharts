package com.nl.treadmilllauncher.view.compassitem;

import android.graphics.Canvas;
import android.graphics.Paint.Style;

import com.nl.treadmilllauncher.view.CompassScaleView;

public class GridRing extends BaseItem {

	public float outRadius;

	public float inRadius;

	public int layerNumber;

	public int piecesNumber;

	public int color;

	public GridRing(CompassScaleView compassScaleView) {
		super(compassScaleView);

		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setColor(color);
		for (int i = 1; i <= layerNumber; i++) {
			float radiusNow = getPxByWeight(i * (outRadius - inRadius) / layerNumber + inRadius);
			canvas.drawCircle(getPxByWeight(centerX), getPxByWeight(centerY), radiusNow, paint);
		}
		for (int i = 0; i < piecesNumber; i++) {
			float angle = i * 360.0f / piecesNumber;

			float x1 = getPxByWeight((float) (centerX + outRadius * Math.sin(Math.toRadians(angle))));
			float y1 = getPxByWeight((float) (centerY + outRadius * Math.cos(Math.toRadians(angle))));

			float x2 = getPxByWeight((float) (centerX + inRadius * Math.sin(Math.toRadians(angle))));
			float y2 = getPxByWeight((float) (centerY + inRadius * Math.cos(Math.toRadians(angle))));

			canvas.drawLine(x1, y1, x2, y2, paint);
		}
	}

	public float getOutRadius() {
		return outRadius;
	}

	public void setOutRadius(float outRadius) {
		this.outRadius = outRadius;
	}

	public float getInRadius() {
		return inRadius;
	}

	public void setInRadius(float inRadius) {
		this.inRadius = inRadius;
	}

	public int getLayerNumber() {
		return layerNumber;
	}

	public void setLayerNumber(int layerNumber) {
		this.layerNumber = layerNumber;
	}

	public int getPiecesNumber() {
		return piecesNumber;
	}

	public void setPiecesNumber(int piecesNumber) {
		this.piecesNumber = piecesNumber;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
