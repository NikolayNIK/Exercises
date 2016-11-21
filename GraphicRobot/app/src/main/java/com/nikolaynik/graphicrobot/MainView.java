package com.nikolaynik.graphicrobot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {

	public static final float CONTROL_ZONE = 0.3F;
	
	private GraphicRobot robot;
	private Paint paint;
	
	public MainView(Context context) {
		super(context);
		robot = new GraphicRobot(0, 0, Direction.UP);
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		robot.render(canvas, paint);
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				return true;
			case MotionEvent.ACTION_UP:
				float x = event.getX();
				if(x < CONTROL_ZONE * getWidth()) {
					robot.turnRight();
				} else if(x > -CONTROL_ZONE * getWidth() + getWidth()) {
					robot.turnLeft();
				} else {
					robot.stepForward();
				}
				invalidate();
				return true;
			default:
				return true;
		}
	}
}
