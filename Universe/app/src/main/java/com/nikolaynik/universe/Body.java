package com.nikolaynik.universe;

import android.graphics.Canvas;

public abstract class Body {
	
	private final String name;
	
	public Body(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract float getX();
	public abstract float getY();
	public abstract void update(float delta);
	public abstract void draw(Canvas canvas);
}
