package com.nikolaynik.universe;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.List;

public abstract class Body {

	public static final byte TYPE_ABSOLUTE = 69;
	public static final byte TYPE_RELATIVE = 34;
	
	private final byte type;
	private final Paint paint;
	
	private String name;
	private float size;
	
	public Body(byte type, String name, float size, int color) {
		this.type = type;
		this.name = name;
		this.size = size;
		this.paint = new Paint();
		this.paint.setColor(color);
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setAntiAlias(true);
	}
	
	public byte getType() {
		return type;
	}
	
	public Paint getPaint() {
		return paint;
	}
	
	public String getName() {
		return name;
	}
	
	public float getSize() {
		return size;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSize(float size) {
		this.size = size;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawCircle(getX(), getY(), size, paint);
	}
	
	public abstract float getX();
	public abstract float getY();
	public abstract void update(float delta);
	public abstract byte[] toByteArray(List<Body> bodies);
	
	public static Body parse(byte[] bytes, List<Body> bodies) {
		switch(bytes[0]) {
			case TYPE_ABSOLUTE:
				return AbsoluteBody.parse(bytes);
			case TYPE_RELATIVE:
				return RelativeBody.parse(bytes, bodies);
			default:
				return null;
		}
	}
}
