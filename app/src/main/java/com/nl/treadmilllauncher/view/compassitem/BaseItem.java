package com.nl.treadmilllauncher.view.compassitem;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.nl.treadmilllauncher.view.CompassScaleView;

import java.util.List;

public abstract class BaseItem {

	public static Bitmap combineToBitmap(int size, List<BaseItem> items) {
		Bitmap bitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		for (BaseItem item : items) {
			item.drawCanvas(canvas);
		}
		return bitmap;
	}

	public float centerX;
	public float centerY;

	protected Paint paint;
	protected CompassScaleView view;

	public BaseItem(CompassScaleView compassScaleView) {
		paint = new Paint();
		paint.setAntiAlias(true);

		this.view = compassScaleView;
	}

	public float getPxByWeight(float weight) {
		return view.getPxByWeight(weight);
	}

	private Bitmap mBitmap;

	public boolean isBitmap() {
		return mBitmap != null;
	}

	public void toBitmap() {
		try {
			if (mBitmap != null) {
				mBitmap.recycle();
				mBitmap = null;
			}
			mBitmap = Bitmap.createBitmap((int) view.mSize, (int) view.mSize, Config.ARGB_8888);
			Canvas canvas = new Canvas(mBitmap);
			drawCanvas(canvas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeBitmap() {
		if (mBitmap != null) {
			mBitmap.recycle();
			mBitmap = null;
		}
	}

	public void draw(Canvas canvas) {
		if (mBitmap == null) {
			drawCanvas(canvas);
		} else {
			canvas.drawBitmap(mBitmap, 0, 0, paint);
		}
	}

	public abstract void drawCanvas(Canvas canvas);
}
