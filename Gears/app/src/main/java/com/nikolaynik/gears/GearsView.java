package com.nikolaynik.gears;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.AttributeSet;

public class GearsView extends View implements Runnable {
	
	public static final float SPEED_MAXIMUM = 30;
	public static final float SPEED_DEFAULT = 10;
	public static final float SPEED_MINIMUM = 1;
	
	private float speed = SPEED_DEFAULT;
	
	private Thread thread;
	private float x0, y0, radius0, angle0;
	private float x1, y1, radius1, angle1;
	
	public GearsView(Context context) {
		super(context);
		resize(getWidth(), getHeight());
	}

	public GearsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		resize(getWidth(), getHeight());
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		if(speed > 0) {
			if(speed > SPEED_MAXIMUM) speed = SPEED_MAXIMUM;
			if(speed < SPEED_MINIMUM) speed = SPEED_MINIMUM;
		} else {
			if(speed < -SPEED_MAXIMUM) speed = -SPEED_MAXIMUM;
			if(speed > -SPEED_MINIMUM) speed = -SPEED_MINIMUM;
		}
		
		this.speed = speed;
	}

	@Override
	protected void onAttachedToWindow() {
		thread = new Thread(this);
		thread.start();
		super.onAttachedToWindow();
	}

	@Override
	public void run() {
		angle1 = 180;
		angle0 = 0;
		int commutator = 0;
		long past = System.currentTimeMillis();
		while(!Thread.interrupted()) {
			long now = System.currentTimeMillis();
			commutator += (now - past);
			if(commutator < 1000 / 60) continue;
			
			float delta = commutator / 1000f * speed;
			commutator = 0;
			
			angle1 += delta;
			angle0 -= delta;
			
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
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		resize(w, h);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void resize(int width, int height) {
		radius0 = width / 4f;
		radius1 = radius0 / 2f;
		
		x0 = 1.1f * radius0;
		y0 = height /2;
		
		x1 = x0 + radius0 + radius1;
		y1 = y0;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStrokeWidth(4);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		
		paint.setColor(Color.argb(128, 0, 0, 255));
		canvas.drawCircle(x0, y0, radius0, paint);
		canvas.drawLine(x0, y0, (float)(Math.cos(Math.toRadians(angle0)) * radius0 + x0), (float)(Math.sin(Math.toRadians(angle0)) * radius0 + y0), paint);

		paint.setColor(Color.argb(128, 255, 64, 0));
		canvas.drawCircle(x1, y1, radius1, paint);
		canvas.drawLine(x1, y1, (float)(Math.cos(Math.toRadians(angle1)) * radius1 + x1), (float)(Math.sin(Math.toRadians(angle1)) * radius1 + y1), paint);
		
		super.onDraw(canvas);
	}
}
