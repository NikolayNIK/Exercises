package com.nikolaynik.universe;

import java.nio.ByteBuffer;
import java.util.List;

public class AbsoluteBody extends Body {
	
	private float x, y;
	
	public AbsoluteBody(String name, float x, float y, float size, int color) {
		super(TYPE_ABSOLUTE, name, size, color);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public byte[] toByteArray(List<Body> bodies) {
		byte[] name = getName().getBytes();
		ByteBuffer fancyBuffer = ByteBuffer.allocate(name.length + 4 + 4 + 4 + 4 + 4 + 1);  // 21 bytes + name
		fancyBuffer.put(TYPE_ABSOLUTE);
		fancyBuffer.putInt(name.length);
		fancyBuffer.put(name);
		fancyBuffer.putFloat(getSize());
		fancyBuffer.putInt(getPaint().getColor());
		fancyBuffer.putFloat(x);
		fancyBuffer.putFloat(y);
		return fancyBuffer.array();
	}
	
	public static AbsoluteBody parse(byte[] bytes) {
		int offset = 1;
		
		int nameLength = ByteBuffer.wrap(bytes, offset, 4).getInt();
		offset += 4;
		
		String name = new String(bytes, offset, nameLength);
		offset += nameLength;
		
		float size = ByteBuffer.wrap(bytes, offset, 4).getFloat();
		offset += 4;
		
		int color = ByteBuffer.wrap(bytes, offset, 4).getInt();
		offset += 4;
		
		float x = ByteBuffer.wrap(bytes, offset, 4).getFloat();
		offset += 4;
		
		float y = ByteBuffer.wrap(bytes, offset, 4).getFloat();
		offset += 4;
		
		return new AbsoluteBody(name, x, y, size, color);
	}
}
