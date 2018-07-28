package com.nl.treadmilllauncher.view.compassitem;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.nl.treadmilllauncher.view.CompassScaleView;

public class IconRing extends BaseItem {

	public float radius;

	public int piecesNumber;

	public int[] icons;

	public int iconWidth;

	public int iconHeight;

	public Drawable[] drawables;

	public IconRing(CompassScaleView compassScaleView) {
		super(compassScaleView);
	}

	private void initIcons() {
		try {
			drawables = new Drawable[piecesNumber];
			for (int i = 0; i < piecesNumber; i++) {
				drawables[i] = this.view.getContext().getResources().getDrawable(icons[i]);
				drawables[i].setBounds(0, 0, iconWidth, iconHeight);
			}
		} catch (Exception e) {
			drawables = null;
			e.printStackTrace();
		}
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		if (icons == null) {
			icons = new int[piecesNumber];
			return;
		}
		if (drawables == null) {
			initIcons();
		}
		if (drawables != null) {
			for (int i = 0; i < piecesNumber; i++) {
				float angle = -i * 360.0f / piecesNumber + 180;

				float x = getPxByWeight((float) (centerX + radius * Math.sin(Math.toRadians(angle))));
				float y = getPxByWeight((float) (centerY + radius * Math.cos(Math.toRadians(angle))));

				canvas.save();
				canvas.translate(x - iconWidth / 2, y - iconHeight / 2);
				drawables[i].draw(canvas);
				canvas.restore();
			}
		}
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getPiecesNumber() {
		return piecesNumber;
	}

	public void setPiecesNumber(int piecesNumber) {
		this.piecesNumber = piecesNumber;
	}

	public int[] getIcons() {
		return icons;
	}

	public void setIcons(int[] icons) {
		this.icons = icons;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}

	public int getIconHeight() {
		return iconHeight;
	}

	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	public Drawable[] getDrawables() {
		return drawables;
	}

	public void setDrawables(Drawable[] drawables) {
		this.drawables = drawables;
	}

}
