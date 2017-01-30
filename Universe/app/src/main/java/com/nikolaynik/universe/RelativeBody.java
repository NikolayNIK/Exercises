package com.nikolaynik.universe;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.nio.ByteBuffer;
import java.util.List;

public class RelativeBody extends Body {

	public static final float ORBIT_HIGHLIGHT_ANTIFACTOR = 5;
	
	private Body parent;
	private float speed, distance, angle;
	
	public RelativeBody(String name, Body parent, float distance, float speed, float angle, float size, int color) {
		super(TYPE_RELATIVE, name, size, color);
		this.parent = parent;
		this.distance = distance;
		this.angle = angle;
		this.speed = speed;
		if(size > ORBIT_HIGHLIGHT_ANTIFACTOR) getPaint().setStrokeWidth(size / ORBIT_HIGHLIGHT_ANTIFACTOR);
		else getPaint().setStrokeWidth(ORBIT_HIGHLIGHT_ANTIFACTOR);
	}

	@Override
	public float getX() {
		return (float)Math.cos(angle) * distance + parent.getX();
	}

	@Override
	public float getY() {
		return (float)Math.sin(angle) * distance + parent.getY();
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public float getDistance() {
		return distance;
	}

	public Body getParent() {
		return parent;
	}

	@Override
	public void setSize(float size) {
		if(size > ORBIT_HIGHLIGHT_ANTIFACTOR) getPaint().setStrokeWidth(size / ORBIT_HIGHLIGHT_ANTIFACTOR);
		else getPaint().setStrokeWidth(ORBIT_HIGHLIGHT_ANTIFACTOR);
		super.setSize(size);
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}

	@Override
	public void draw(Canvas canvas) {
		float x = parent.getX();
		float y = parent.getY();
		getPaint().setStyle(Paint.Style.STROKE);
		getPaint().setAlpha(128);
		canvas.drawCircle(x, y, distance, getPaint());
		getPaint().setStyle(Paint.Style.FILL);
		getPaint().setAlpha(255);
		super.draw(canvas);
	}
	
	@Override
	public void update(float delta) {
		angle += speed * delta;
	}
	
	@Override
	public byte[] toByteArray(List<Body> bodies) {
		byte[] name = getName().getBytes();
		ByteBuffer fancyBuffer = ByteBuffer.allocate(name.length + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 1); // 29 bytes + name
		fancyBuffer.put(TYPE_RELATIVE);
		fancyBuffer.putInt(name.length);
		fancyBuffer.put(name);
		fancyBuffer.putFloat(getSize());
		fancyBuffer.putInt(getPaint().getColor());
		fancyBuffer.putFloat(getDistance());
		fancyBuffer.putFloat(getSpeed());
		fancyBuffer.putFloat(angle);
		fancyBuffer.putInt(bodies.indexOf(getParent()));
		return fancyBuffer.array();
	}
	
	public static RelativeBody parse(byte[] bytes, List<Body> bodies) {
		int offset = 1;

		int nameLength = ByteBuffer.wrap(bytes, offset, 4).getInt();
		offset += 4;

		String name = new String(bytes, offset, nameLength);
		offset += nameLength;

		float size = ByteBuffer.wrap(bytes, offset, 4).getFloat();
		offset += 4;

		int color = ByteBuffer.wrap(bytes, offset, 4).getInt();
		offset += 4;

		float distance = ByteBuffer.wrap(bytes, offset, 4).getFloat();
		offset += 4;
		
		float speed = ByteBuffer.wrap(bytes, offset, 4).getFloat();
		offset += 4;
		
		float angle = ByteBuffer.wrap(bytes, offset, 4).getFloat();
		offset += 4;
		
		int parent = ByteBuffer.wrap(bytes, offset, 4).getInt();
		offset += 4;
		
		return new RelativeBody(name, bodies.get(parent), distance, speed, angle, size, color);
	}
}
