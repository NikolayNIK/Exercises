package com.nikolaynik.graphicrobot;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Rect;

public class GraphicRobot extends Robot {
	
	public static final int CELL_SIZE = 64;
	public static final int ROBOT_PADDING = 4;
	
	private final MainView view;
	
	private MoveTask move;
	
	public GraphicRobot(MainView view, int x, int y, Direction dir) {
		super(x, y, dir);
		this.view = view;
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
		paint.setColor(Color.argb(128, 128, 128, 128));
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(4);
		
		for(int x = 0; x < canvas.getWidth(); x += CELL_SIZE)
			canvas.drawLine(x, 0, x, canvas.getHeight(), paint);
		
		for(int y = 0; y < canvas.getHeight(); y += CELL_SIZE)
			canvas.drawLine(0, y, canvas.getWidth(), y, paint);
		
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		Rect bounds = new Rect(getX() * CELL_SIZE + ROBOT_PADDING, getY() * CELL_SIZE + ROBOT_PADDING, getX() * CELL_SIZE + ROBOT_PADDING + (ROBOT_PADDING * -2 + CELL_SIZE), getY() * CELL_SIZE + ROBOT_PADDING + (ROBOT_PADDING * -2 + CELL_SIZE));
		canvas.drawRect(bounds, paint);
		
		paint.setAntiAlias(true);
		switch(getDirection()) {
			case UP:
				canvas.drawCircle(bounds.centerX(), bounds.centerY() + (bounds.height() / 2), ROBOT_PADDING, paint);
				break;
			case RIGHT:
				canvas.drawCircle(bounds.centerX() + (bounds.width() / 2), bounds.centerY(), ROBOT_PADDING, paint);
				break;
			case DOWN:
				canvas.drawCircle(bounds.centerX(), bounds.centerY() - (bounds.height() / 2), ROBOT_PADDING, paint);
				break;
			case LEFT:
				canvas.drawCircle(bounds.centerX() - (bounds.width() / 2), bounds.centerY(), ROBOT_PADDING, paint);
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
			this(x, y, 500);
		}

		@Override
		public synchronized void run() {
			while(!Thread.interrupted()) try {
				Thread.sleep(delay);
				
				if(x > getX()) {
					switch(getDirection()) {
						case UP:
							turnRight();
							view.postInvalidate();
							continue;
						case RIGHT:
							stepForward();
							view.postInvalidate();
							continue;
						case DOWN:
							turnLeft();
							view.postInvalidate();
							continue;
						case LEFT:
							turnRight();
							view.postInvalidate();
							continue;
					}
				}
				
				if(x < getX()) {
					switch(getDirection()) {
						case UP:
							turnLeft();
							view.postInvalidate();
							continue;
						case LEFT:
							stepForward();
							view.postInvalidate();
							continue;
						case DOWN:
							turnRight();
							view.postInvalidate();
							continue;
						case RIGHT:
							turnRight();
							view.postInvalidate();
							continue;
					}
				}
				
				if(y > getY()) {
					switch(getDirection()) {
						case LEFT:
							turnRight();
							view.postInvalidate();
							continue;
						case UP:
							stepForward();
							view.postInvalidate();
							continue;
						case RIGHT:
							turnLeft();
							view.postInvalidate();
							continue;
						case DOWN:
							turnRight();
							view.postInvalidate();
							continue;
					}
				}

				if(y < getY()) {
					switch(getDirection()) {
						case LEFT:
							turnLeft();
							view.postInvalidate();
							continue;
						case DOWN:
							stepForward();
							view.postInvalidate();
							continue;
						case RIGHT:
							turnRight();
							view.postInvalidate();
							continue;
						case UP:
							turnRight();
							view.postInvalidate();
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
