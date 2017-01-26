package com.nikolaynik.universe;

import android.graphics.Canvas;
import android.graphics.Paint;

public class RelativeBody extends Body {

	private final Body parent;
	private final Paint paint;
	private final float distance, speed, size;
	
	private float angle;
	
	public RelativeBody(String name, Body parent, float distance, float speed, float size, int color) {
		super(name);
		this.parent = parent;
		this.distance = distance;
		this.speed = speed;
		this.size = size;
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setColor(color);
	}

	@Override
	public float getX() {
		return (float)Math.cos(angle) * distance + parent.getX();
	}

	@Override
	public float getY() {
		return (float)Math.sin(angle) * distance + parent.getY();
	}

	@Override
	public void update(float delta) {
		angle += speed * delta;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(getX(), getY(), size, paint);
	}
}
