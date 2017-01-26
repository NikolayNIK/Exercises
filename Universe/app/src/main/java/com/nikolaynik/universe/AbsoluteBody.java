package com.nikolaynik.universe;

import android.graphics.Canvas;
import android.graphics.Paint;

public class AbsoluteBody extends Body {

	private final Paint paint;
	private final float x, y, size;
	
	public AbsoluteBody(String name, float x, float y, float size, int color) {
		super(name);
		this.x = x;
		this.y = y;
		this.size = size;
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setColor(color);
	}
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(getX(), getY(), size, paint);
	}
}
