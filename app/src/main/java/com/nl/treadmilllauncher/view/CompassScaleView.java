package com.nl.treadmilllauncher.view;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.view.compassitem.BaseItem;
import com.nl.treadmilllauncher.view.compassitem.GridRing;
import com.nl.treadmilllauncher.view.compassitem.GridRingPoint;
import com.nl.treadmilllauncher.view.compassitem.IconRing;
import com.nl.treadmilllauncher.view.compassitem.LabelRing;
import com.nl.treadmilllauncher.view.compassitem.ProgressRing;
import com.nl.treadmilllauncher.view.compassitem.Ring;

public class CompassScaleView extends View {

	private static final String TAG = "CompassScaleView";

	private float totalSizeWeight = 1800;

	public float mSize;

	private List<Ring> rings;

	private List<ProgressRing> progressRings;

	private List<LabelRing> labelRings;

	private ValueAnimator timeRingAnimator;

	private ValueAnimator timePointAnimator;

	/**
	 * 数值百分比,各个项分别为 [心率0,热身1,极限冲刺2,慢走3,脂肪燃烧4,乳酸阈值5]
	 */
	private int[] endValuePercent = { 0, 0, 0, 0, 0, 0 };

	private float distance = 0.0f;

	private int[] pircesColors = new int[] { 0x6C42E6, 0x661777E2, 0x6616E177, 0x66FFBC3A, 0x66E12DA1, 0x66B033E2 };
	private int[] pointColors = new int[] { 0xFFE12DA1, 0xFFB033E2, 0xFF6C42E6, 0xFF1777E2, 0xFF16E177, 0xFFFFBC3A };

	private GridRing gridRing;

	private GridRingPoint gridRingPoint;

	private IconRing iconRing;

	private Bitmap mBitmap;
	private List<BaseItem> items;

	public CompassScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public CompassScaleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CompassScaleView(Context context) {
		super(context);
		init();
	}

	private void init() {
		items = new ArrayList<BaseItem>();
		rings = new ArrayList<Ring>();
		progressRings = new ArrayList<ProgressRing>();
		labelRings = new ArrayList<LabelRing>();
		initRings();
		initProgressRing();
		initLabelRing();
		initGridRing();
	}

	private void initGridRing() {
		gridRing = new GridRing(this);
		gridRing.color = 0xbf596b82;
		gridRing.centerX = totalSizeWeight / 2;
		gridRing.centerY = totalSizeWeight / 2;
		gridRing.inRadius = 375;
		gridRing.outRadius = 600;
		gridRing.layerNumber = 3;
		gridRing.piecesNumber = 6;

		gridRingPoint = new GridRingPoint(this);
		gridRingPoint.pircesColors = pircesColors;
		gridRingPoint.pointColors = pointColors;
		gridRingPoint.pircesScores = new int[] { 60, 40, 70, 80, 45, 50};
		gridRingPoint.centerX = totalSizeWeight / 2;
		gridRingPoint.centerY = totalSizeWeight / 2;
		gridRingPoint.inRadius = 375;
		gridRingPoint.pointSize = 15;
		gridRingPoint.outRadius = 600;
		gridRingPoint.piecesNumber = 6;

		iconRing = new IconRing(this);
		iconRing.piecesNumber = 6;
		iconRing.iconWidth = (int)getResources().getDimension(R.dimen.x53);
		iconRing.iconHeight = (int)getResources().getDimension(R.dimen.x49);
		iconRing.radius = 750;
		iconRing.centerX = totalSizeWeight / 2;
		iconRing.centerY = totalSizeWeight / 2;
		iconRing.icons = new int[] { R.mipmap.heart_lung_enhancement, R.mipmap.warm_up, R.mipmap.limit_sprint, R.mipmap.casualness,
				R.mipmap.fat_combustion, R.mipmap.lactic_acid_threshold};
	}

	private void initRings() {
		Ring ring1 = new Ring(this);
		ring1.ringWidth = getResources().getDimension(R.dimen.x3);
		ring1.ringAngle = 360;
		ring1.startAngle = 0;
		ring1.ringRadius = 300;
		ring1.ringSpeed = 500;
		ring1.ringColor = 0xbf66e1f5;
		ring1.centerX = totalSizeWeight / 2;
		ring1.centerY = totalSizeWeight / 2;
		addRing(ring1);

		Ring ring2 = new Ring(this);
		ring2.ringWidth = getResources().getDimension(R.dimen.x17);
		ring2.ringAngle = 120;
		ring2.startAngle = -135;
		ring2.ringRadius = 220;
		ring2.ringSpeed = -500;
		ring2.ringColor = 0x6666e1f5;
		ring2.centerX = totalSizeWeight / 2;
		ring2.centerY = totalSizeWeight / 2;
		addRing(ring2);

		Ring ring3 = new Ring(this);
		ring3.ringWidth = getResources().getDimension(R.dimen.x5);
		ring3.ringAngle = 230;
		ring3.startAngle = -45;
		ring3.ringRadius = 230;
		ring3.ringSpeed = 30;
		ring3.ringColor = 0xbf66e1f5;
		ring3.centerX = totalSizeWeight / 2;
		ring3.centerY = totalSizeWeight / 2;
		addRing(ring3);

		Ring ring4 = new Ring(this);
		ring4.ringWidth = 2;
		ring4.ringAngle = 180;
		ring4.startAngle = 15;
		ring4.ringRadius = 200;
		ring4.ringSpeed = -300;
		ring4.ringColor = 0xbf66e1f5;
		ring4.centerX = totalSizeWeight / 2;
		ring4.centerY = totalSizeWeight / 2;
		addRing(ring4);

		Ring ring5 = new Ring(this);
		ring5.ringWidth = getResources().getDimension(R.dimen.x6);
		ring5.ringAngle = 180;
		ring5.startAngle = 135;
		ring5.ringRadius = 175;
		ring5.ringSpeed = 300;
		ring5.ringColor = 0xbf66e1f5;
		ring5.centerX = totalSizeWeight / 2;
		ring5.centerY = totalSizeWeight / 2;
		addRing(ring5);
	}

	private void initProgressRing() {
		ProgressRing progressRing1 = new ProgressRing(this);
		progressRing1.ringWidth = getResources().getDimension(R.dimen.x13);
		progressRing1.ringRadius = 330;
		progressRing1.ringColor = 0xff292929;
		progressRing1.progressStartColor = 0xff0e4c8f;
		progressRing1.progressEndColor = 0xff15b5d2;
		progressRing1.ringCount = 120;
		progressRing1.centerX = totalSizeWeight / 2;
		progressRing1.centerY = totalSizeWeight / 2;
		progressRing1.progressStartCount = 60;
		progressRing1.progressCount = 55;
		addProgressRing(progressRing1);

		ProgressRing progressRing2 = new ProgressRing(this);
		progressRing2.ringWidth = getResources().getDimension(R.dimen.x17);
		progressRing2.ringRadius = 270;
		progressRing2.ringColor = 0xff000000;
		progressRing2.progressStartColor = 0xff21474c;
		progressRing2.progressEndColor = 0xff21474c;
		progressRing2.ringCount = 120;
		progressRing2.centerX = totalSizeWeight / 2;
		progressRing2.centerY = totalSizeWeight / 2;
		progressRing2.progressStartCount = 25;
		progressRing2.progressCount = 30;
		addProgressRing(progressRing2);
	}

	private void initLabelRing() {
		float angle = 50;
		float width = getResources().getDimension(R.dimen.x17);

		LabelRing labelRing1 = new LabelRing(this);
		labelRing1.labelText = "心肺提升";
		labelRing1.labelColor = pointColors[0];
		labelRing1.labelWidth = width;
		labelRing1.labelAngle = angle;
		labelRing1.labelRadius = 375;
		labelRing1.centerX = totalSizeWeight / 2;
		labelRing1.centerY = totalSizeWeight / 2;
		labelRing1.labelCenterAngle = 0;
		addLabelRing(labelRing1);

		LabelRing labelRing2 = new LabelRing(this);
		labelRing2.labelText = "慢跑热身";
		labelRing2.labelColor = pointColors[1];
		labelRing2.labelWidth = width;
		labelRing2.labelAngle = angle;
		labelRing2.labelRadius = 375;
		labelRing2.centerX = totalSizeWeight / 2;
		labelRing2.centerY = totalSizeWeight / 2;
		labelRing2.labelCenterAngle = 60;
		addLabelRing(labelRing2);

		LabelRing labelRing3 = new LabelRing(this);
		labelRing3.labelText = "极限冲刺";
		labelRing3.labelColor = pointColors[2];
		labelRing3.labelWidth = width;
		labelRing3.labelAngle = angle;
		labelRing3.labelRadius = 375;
		labelRing3.centerX = totalSizeWeight / 2;
		labelRing3.centerY = totalSizeWeight / 2;
		labelRing3.labelCenterAngle = 120;
		addLabelRing(labelRing3);

		LabelRing labelRing4 = new LabelRing(this);
		labelRing4.labelText = "轻度慢走";
		labelRing4.labelColor = pointColors[3];
		labelRing4.labelWidth = width;
		labelRing4.labelAngle = angle;
		labelRing4.labelRadius = 375;
		labelRing4.centerX = totalSizeWeight / 2;
		labelRing4.centerY = totalSizeWeight / 2;
		labelRing4.labelCenterAngle = 180;
		addLabelRing(labelRing4);

		LabelRing labelRing5 = new LabelRing(this);
		labelRing5.labelText = "脂肪燃烧";
		labelRing5.labelColor = pointColors[4];
		labelRing5.labelWidth = width;
		labelRing5.labelAngle = angle;
		labelRing5.labelRadius = 375;
		labelRing5.centerX = totalSizeWeight / 2;
		labelRing5.centerY = totalSizeWeight / 2;
		labelRing5.labelCenterAngle = 240;
		addLabelRing(labelRing5);

		LabelRing labelRing6 = new LabelRing(this);
		labelRing6.labelText = "乳酸阈值";
		labelRing6.labelColor = pointColors[5];
		labelRing6.labelWidth = width;
		labelRing6.labelAngle = angle;
		labelRing6.labelRadius = 375;
		labelRing6.centerX = totalSizeWeight / 2;
		labelRing6.centerY = totalSizeWeight / 2;
		labelRing6.labelCenterAngle = 300;
		addLabelRing(labelRing6);
	}

	private boolean initSize() {
		float mHeight = getHeight();
		float mWidth = getWidth();
		mSize = mWidth <= mHeight ? mWidth : mHeight;
		return mSize != 0;
	}

	public void startPointAnimator(final int timemillis) {
		if (timePointAnimator != null) {
			if (timePointAnimator.isRunning()) {
				return;
			}
		}
		timePointAnimator = ValueAnimator.ofInt(0, timemillis);
		timePointAnimator.setInterpolator(new BounceInterpolator());
		timePointAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int timemillisValue = (Integer) animation.getAnimatedValue();
				int[] datasNow = new int[6];
				for (int i = 0; i < 6; i++) {
					datasNow[i] = (int) ((float) endValuePercent[i] * (float) timemillisValue / timemillis);
				}
				gridRingPoint.pircesScores = datasNow;
				postInvalidate();
			}
		});
		timePointAnimator.setDuration(timemillis);
		timePointAnimator.start();
	}

	public void stopRingAnimator() {
		Log.d(TAG, "stopRingAnimator");
		if (timeRingAnimator != null) {
			timeRingAnimator.cancel();
			timeRingAnimator = null;
		}
	}

	public void startRingAnimator(int timemillis, int repeatCount) {
		Log.d(TAG,"startRingAnimator -> " + timemillis);
		if (timeRingAnimator != null) {
			if (timeRingAnimator.isRunning()) {
				return;
			}
		}
		timeRingAnimator = ValueAnimator.ofInt(0, timemillis);
		timeRingAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int timemillisValue = (Integer) animation.getAnimatedValue();
				for (Ring ring : rings) {
					float offsetAngle = ring.ringSpeed * timemillisValue / 1000.0f;
					ring.offsetAngle = offsetAngle;
				}
				postInvalidate();
			}
		});
		timeRingAnimator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				for (Ring ring : rings) {
					ring.startAngle = ring.startAngle + ring.offsetAngle;
					ring.offsetAngle = 0;
				}
				invalidate();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		timeRingAnimator.setDuration(timemillis);
		timeRingAnimator.setRepeatCount(repeatCount);
		timeRingAnimator.start();
	}

	public void addRing(Ring ring) {
		rings.add(ring);
	}

	public void addProgressRing(ProgressRing progressRing) {
		progressRings.add(progressRing);
	}

	public void addLabelRing(LabelRing labelRing) {
		labelRings.add(labelRing);
	}

	Paint coordinatesPaint;

	private void drawCoordinatesSystem(Canvas canvas) {
		if (coordinatesPaint == null) {
			coordinatesPaint = new Paint();
			coordinatesPaint.setColor(Color.GREEN);
			coordinatesPaint.setStrokeWidth(1);
		}
		for (int i = 0; i < totalSizeWeight; i += 100) {
			canvas.drawLine(0, getPxByWeight(i), getPxByWeight(totalSizeWeight), getPxByWeight(i), coordinatesPaint);
			canvas.drawLine(getPxByWeight(i), 0, getPxByWeight(i), getPxByWeight(totalSizeWeight), coordinatesPaint);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (initSize()) {
			if (gridRing != null) {
				if (!gridRing.isBitmap()) {
					gridRing.toBitmap();
				}
				gridRing.draw(canvas);
			}
			if (gridRingPoint != null) {
				gridRingPoint.draw(canvas);
			}
			if (mBitmap == null) {
				items.clear();
				if (iconRing != null) {
					if (!iconRing.isBitmap()) {
						iconRing.toBitmap();
					}
					items.add(iconRing);
				}
				for (ProgressRing progressRing : progressRings) {
					if (!progressRing.isBitmap()) {
						progressRing.toBitmap();
					}
					items.add(progressRing);
				}
				for (LabelRing labelRing : labelRings) {
					if (!labelRing.isBitmap()) {
						labelRing.toBitmap();
					}
					items.add(labelRing);
				}
				mBitmap = BaseItem.combineToBitmap((int) mSize, items);
			}
			canvas.drawBitmap(mBitmap, 0, 0, coordinatesPaint);
			for (Ring ring : rings) {
				ring.drawCanvas(canvas);
			}
			drawCenterText(canvas);
		}
	}

	private void drawCenterText(Canvas canvas) {

		int color = Color.parseColor("#64ddf2");
		String value = "" + distance;
		int centerX = getWidth() / 2;
		int centerY = getHeight() /2;

		Paint vTextPaint = new Paint();
		vTextPaint.setTextSize(getResources().getDimension(R.dimen.x48));
		vTextPaint.setTextAlign(Paint.Align.CENTER);
		vTextPaint.setAntiAlias(true);//抗锯齿功能
		vTextPaint.setColor(color);
		Rect bounds = new Rect();
		vTextPaint.getTextBounds(value, 0, value.length(), bounds);
		Paint.FontMetricsInt fontMetrics = vTextPaint.getFontMetricsInt();
		int baseline = (getHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
		canvas.drawText(value, centerX, baseline - getResources().getDimension(R.dimen.x13), vTextPaint);
		canvas.save();

		bounds = new Rect();
		vTextPaint.setTextSize(getResources().getDimension(R.dimen.x18));
		fontMetrics = vTextPaint.getFontMetricsInt();
		baseline = (getHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
		vTextPaint.getTextBounds("公里", 0, 2, bounds);
		canvas.drawText("公里", centerX, baseline + getResources().getDimension(R.dimen.x33), vTextPaint);

		Paint mPaint = new Paint();
		mPaint.setStrokeWidth(2);
		mPaint.setColor(color);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawLine(centerX * 6 / 7, centerY + getResources().getDimension(R.dimen.x16),
				centerX * 8 / 7, centerY + getResources().getDimension(R.dimen.x16), mPaint);
	}

	public void clearBitmap() {
		try {
			for (Ring ring : rings) {
				ring.removeBitmap();
			}
			for (ProgressRing progressRing : progressRings) {
				progressRing.removeBitmap();
			}
			for (LabelRing labelRing : labelRings) {
				labelRing.removeBitmap();
			}
			if (gridRing != null) {
				gridRing.removeBitmap();
			}
			if (gridRingPoint != null) {
				gridRingPoint.removeBitmap();
			}
			if (iconRing != null) {
				iconRing.removeBitmap();
			}
			if (mBitmap != null) {
				mBitmap.recycle();
			}
			for (BaseItem item : items) {
				item.removeBitmap();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getPxByWeight(float weight) {
		return weight * mSize / totalSizeWeight;
	}

	/**
	 * 数值百分比,各个项分别为 [心率0,热身1,极限冲刺2,慢走3,脂肪燃烧4,乳酸阈值5]
	 *
	 * @param datas
	 */
	public void setData(int[] datas) {
		endValuePercent = datas;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
}
