package com.nikolaynik.graphicrobot;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Rect;

public class GraphicRobot extends Robot {
	
	public static final int CELL_SIZE = 128;
	public static final int ROBOT_PADDING = 4;
	
	public GraphicRobot(int x, int y, Direction dir) {
		super(x, y, dir);
	}
	
	public void render(Canvas canvas, Paint paint) {
		paint.setColor(Color.YELLOW);
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
}
