package com.nl.treadmilllauncher.view.compassitem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.SweepGradient;

import com.nl.treadmilllauncher.view.CompassScaleView;

public class GridRingPoint extends BaseItem {

	public int pointSize;

	public int color;

	public float outRadius;

	public float inRadius;

	public int piecesNumber;

	public int[] pircesColors;

	public int[] pointColors;

	public int[] pircesScores;

	private Path mPath;

	private SweepGradient gradient;

	public GridRingPoint(CompassScaleView compassScaleView) {
		super(compassScaleView);
		mPath = new Path();
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		if (pircesColors == null) {
			pircesColors = new int[piecesNumber];
			return;
		}
		if (pircesScores == null) {
			pircesScores = new int[piecesNumber];
			return;
		}
		if (pointColors == null) {
			pointColors = new int[piecesNumber];
			return;
		}
		if (gradient == null) {
			int[] mColor = new int[piecesNumber + 1];
			System.arraycopy(pircesColors, 0, mColor, 0, piecesNumber);
			mColor[piecesNumber] = pircesColors[0];
			gradient = new SweepGradient(getPxByWeight(centerX), getPxByWeight(centerY), mColor, null);
//			Matrix matrix = new Matrix();
//			matrix.setRotate(30f, canvas.getWidth() / 2, canvas.getHeight() / 2);
//			gradient.setLocalMatrix(matrix);
		}

		mPath.rewind();
		for (int i = 0; i < piecesNumber; i++) {
			float angle = -i * 360.0f / piecesNumber + 180;
			float radiusNow = (pircesScores[i] / 100.0f) * (outRadius - inRadius) + inRadius;

			float x = getPxByWeight((float) (centerX + radiusNow * Math.sin(Math.toRadians(angle))));
			float y = getPxByWeight((float) (centerY + radiusNow * Math.cos(Math.toRadians(angle))));
			if (i == 0) {
				mPath.moveTo(x, y);
			}

			int nextI = (i + 1) % piecesNumber;
			float nextAngle = -nextI * 360.0f / piecesNumber + 180;
			float radiusNextNow = (pircesScores[nextI] / 100.0f) * (outRadius - inRadius) + inRadius;
			float nextX = getPxByWeight((float) (centerX + radiusNextNow * Math.sin(Math.toRadians(nextAngle))));
			float nextY = getPxByWeight((float) (centerY + radiusNextNow * Math.cos(Math.toRadians(nextAngle))));
			mPath.lineTo(nextX, nextY);
		}
		mPath.close();
		paint.setStyle(Style.FILL);
		paint.setShader(gradient);
		canvas.drawPath(mPath, paint);
		paint.setShader(null);
		for (int i = 0; i < piecesNumber; i++) {
			float angle = -i * 360.0f / piecesNumber + 180;
			float radiusNow = (pircesScores[i] / 100.0f) * (outRadius - inRadius) + inRadius;

			float x = getPxByWeight((float) (centerX + radiusNow * Math.sin(Math.toRadians(angle))));
			float y = getPxByWeight((float) (centerY + radiusNow * Math.cos(Math.toRadians(angle))));

			paint.setColor(pointColors[i]);
			paint.setStyle(Style.FILL);
			canvas.drawCircle(x, y, getPxByWeight(pointSize), paint);
		}
		paint.setColor(Color.BLACK);
		canvas.drawCircle(getPxByWeight(centerX), getPxByWeight(centerY), getPxByWeight(inRadius), paint);
	}

	public int getPointSize() {
		return pointSize;
	}

	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
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

	public int getPiecesNumber() {
		return piecesNumber;
	}

	public void setPiecesNumber(int piecesNumber) {
		this.piecesNumber = piecesNumber;
	}

	public int[] getPircesColors() {
		return pircesColors;
	}

	public void setPircesColors(int[] pircesColors) {
		this.pircesColors = pircesColors;
	}

	public int[] getPointColors() {
		return pointColors;
	}

	public void setPointColors(int[] pointColors) {
		this.pointColors = pointColors;
	}

	public int[] getPircesScores() {
		return pircesScores;
	}

	public void setPircesScores(int[] pircesScores) {
		this.pircesScores = pircesScores;
	}

	public Path getmPath() {
		return mPath;
	}

	public void setmPath(Path mPath) {
		this.mPath = mPath;
	}

	public SweepGradient getGradient() {
		return gradient;
	}

	public void setGradient(SweepGradient gradient) {
		this.gradient = gradient;
	}

}
