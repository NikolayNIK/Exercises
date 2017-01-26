package com.nikolaynik.universe;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import java.util.List;
import java.util.ArrayList;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class UniverseView extends View implements Runnable, GestureDetector.OnGestureListener {

	private final List<Body> bodies;
	private final GestureDetector gestureDetector;
	
	private Thread thread;
	private float x, y;
	
	public UniverseView(Context c) {
		super(c);
		this.bodies = new ArrayList<>();
		this.gestureDetector = new GestureDetector(this);
	}

	public UniverseView(Context c, AttributeSet attrs) {
		super(c, attrs);
		this.bodies = new ArrayList<>();
		this.gestureDetector = new GestureDetector(this);
	}

	public List<Body> getBodies() {
		return bodies;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.x = w / -2f;
		this.y = h / -2f;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(-x, -y);
		for(Body body: bodies) body.draw(canvas);
		super.onDraw(canvas);
	}
	
	@Override
	protected void onAttachedToWindow() {
		thread = new Thread(this);
		thread.start();
		super.onAttachedToWindow();
	}

	@Override
	public void run() {
		int commutator = 0;
		long past = System.currentTimeMillis();
		while(!Thread.interrupted()) {
			long now = System.currentTimeMillis();
			commutator += (now - past);
			if(commutator < 1000 / 60) continue;

			float delta = commutator / 1000f;
			commutator = 0;
			
			for(Body body: bodies) body.update(delta);
			
			postInvalidate();
			past = now;
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		thread.interrupt();
		super.onDetachedFromWindow();
	}

	@Override
	public boolean onDown(MotionEvent p1) {
		return true;
	}

	@Override
	public void onShowPress(MotionEvent p1) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent p1) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent p1, MotionEvent p2, float x, float y) {
		this.x += x;
		this.y += y;
		return true;
	}

	@Override
	public void onLongPress(MotionEvent p1) {
		
	}

	@Override
	public boolean onFling(MotionEvent p1, MotionEvent p2, float p3, float p4) {
		return false;
	}
}