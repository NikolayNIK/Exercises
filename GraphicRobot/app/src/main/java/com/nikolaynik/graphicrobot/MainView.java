package com.nikolaynik.graphicrobot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;

public class MainView extends View implements OnGestureListener {

	public static final float CONTROL_ZONE = 0.3F;
	
	private GraphicRobot robot;
	private Paint paint;
	private GestureDetector gestureDetector;
	
	public MainView(Context context) {
		super(context);
		robot = new GraphicRobot(0, 0, Direction.UP);
		paint = new Paint();
		gestureDetector = new GestureDetector(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.reset();
		robot.render(canvas, paint);
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(gestureDetector.onTouchEvent(event)) {
			invalidate();
			return true;
		}
		
		return false;
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
		robot.stepForward();
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent p1, MotionEvent p2, float x, float y) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent p1) {
		
	}

	@Override
	public boolean onFling(MotionEvent p1, MotionEvent p2, float x, float y) {
		if(x > 0) {
			robot.turnLeft();
			return true;
		}

		if(x < 0) {
			robot.turnRight();
			return true;
		}
		
		return false;
	}
}
