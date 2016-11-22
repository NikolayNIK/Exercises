package com.nikolaynik.graphicrobot;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Rect;

public class GraphicRobot extends Robot {
	
	public static final int CELL_SIZE = 64;
	public static final int ROBOT_PADDING = 6;
	public static final int ROBOT_COLOR = Color.argb(255, 51, 181, 229);
	public static final int BORDER_COLOR = Color.argb(128, 128, 128, 128);
	
	private final MainView view;
	
	private MoveTask move;
	
	public GraphicRobot(MainView view, int x, int y, Direction dir) {
		super(x, y, dir);
		this.view = view;
	}

	@Override
	public void turnLeft() {
		super.turnLeft();
		view.postInvalidate();
	}

	@Override
	public void turnRight() {
		super.turnRight();
		view.postInvalidate();
	}

	@Override
	public void stepForward() {
		super.stepForward();
		view.postInvalidate();
	}
	
	public void moveTo(int x, int y) {
		cancelMove();
		move = new MoveTask(x, y);
		move.start();
	}
	
	public void cancelMove() {
		if(move != null) {
			move.interrupt();
			while(move.isAlive());
			move = null;
		}
	}
	
	public void render(Canvas canvas, Paint paint) {
		paint.setColor(BORDER_COLOR);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(4);
		
		for(int x = 0; x < canvas.getWidth(); x += CELL_SIZE)
			canvas.drawLine(x, 0, x, canvas.getHeight(), paint);

		for(int y = 0; y < canvas.getHeight(); y += CELL_SIZE)
			canvas.drawLine(0, y, canvas.getWidth(), y, paint);
		
		Rect bounds = new Rect();
		bounds.left = getX() * CELL_SIZE + ROBOT_PADDING;
		bounds.top = getY() * CELL_SIZE + ROBOT_PADDING;
		bounds.right = bounds.left + CELL_SIZE - (2 * ROBOT_PADDING);
		bounds.bottom = bounds.top + CELL_SIZE - (2 * ROBOT_PADDING);
		
		paint.setColor(ROBOT_COLOR);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		
		canvas.drawRect(bounds, paint);
		
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		
		switch(getDirection()) {
			case UP:
				canvas.drawCircle(bounds.centerX(), bounds.bottom, ROBOT_PADDING, paint);
				break;
			case DOWN:
				canvas.drawCircle(bounds.centerX(), bounds.top, ROBOT_PADDING, paint);
				break;
			case RIGHT:
				canvas.drawCircle(bounds.right, bounds.centerY(), ROBOT_PADDING, paint);
				break;
			case LEFT:
				canvas.drawCircle(bounds.left, bounds.centerY(), ROBOT_PADDING, paint);
				break;
		}
	}
	
	public class MoveTask extends Thread {
		
		private final int x, y, delay;
		
		public MoveTask(int x, int y, int delay) {
			this.x = x;
			this.y = y;
			this.delay = delay;
		}

		public MoveTask(int x, int y) {
			this(x, y, 400);
		}

		@Override
		public synchronized void run() {
			while(!Thread.interrupted()) try {
				Thread.sleep(delay);
				
				if(x > getX()) {
					switch(getDirection()) {
						case UP:
							turnRight();
							continue;
						case RIGHT:
							stepForward();
							continue;
						case DOWN:
							turnLeft();
							continue;
						case LEFT:
							turnRight();
							continue;
					}
				}
				
				if(x < getX()) {
					switch(getDirection()) {
						case UP:
							turnLeft();
							continue;
						case LEFT:
							stepForward();
							continue;
						case DOWN:
							turnRight();
							continue;
						case RIGHT:
							turnRight();
							continue;
					}
				}
				
				if(y > getY()) {
					switch(getDirection()) {
						case LEFT:
							turnRight();
							continue;
						case UP:
							stepForward();
							continue;
						case RIGHT:
							turnLeft();
							continue;
						case DOWN:
							turnRight();
							continue;
					}
				}

				if(y < getY()) {
					switch(getDirection()) {
						case LEFT:
							turnLeft();
							continue;
						case DOWN:
							stepForward();
							continue;
						case RIGHT:
							turnRight();
							continue;
						case UP:
							turnRight();
							continue;
					}
				}
				
				break;
			} catch(InterruptedException e) {
				break;
			}
		}
	}
}
