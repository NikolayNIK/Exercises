package com.nikolaynik.universe;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class UniverseView extends View implements Runnable, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {

	public static final byte[] FORMAT_SIGN = new byte[] {0x34, 0x47, 0x69};
	
	private final List<Body> bodies;
	private final GestureDetector gestureDetector;
	private final ScaleGestureDetector scaleGestureDetector;
	
	private final int minDelta, maxDelta;
	
	private Thread thread;
	private float x, y, scale;
	
	public UniverseView(Context c) {
		super(c);
		this.bodies = new ArrayList<>();
		this.gestureDetector = new GestureDetector(this);
		this.scaleGestureDetector = new ScaleGestureDetector(c, this);

		this.minDelta = 1000 / (int)((WindowManager)c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRefreshRate();
		this.maxDelta = minDelta * 4;
	}

	public UniverseView(Context c, AttributeSet attrs) {
		super(c, attrs);
		this.bodies = new ArrayList<>();
		this.gestureDetector = new GestureDetector(this);
		this.scaleGestureDetector = new ScaleGestureDetector(c, this);

		this.minDelta = 500 / (int)((WindowManager)c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRefreshRate();
		this.maxDelta = minDelta * 4;
	}

	public List<Body> getBodies() {
		return bodies;
	}
	
	public void center() {
		x = getWidth() / -2f;
		y = getHeight() / -2f;
		scale = 1;
	}
	
	public void open(File file) {
		new OpenTask(file).execute();
	}

	public void save(File file) {
		new SaveTask(file).execute();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = scaleGestureDetector.onTouchEvent(event);
		result |= gestureDetector.onTouchEvent(event);
		
		return result;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		center();
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(-x, -y);
		canvas.scale(scale, scale);
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
			if(commutator < minDelta) continue;
			if(commutator > maxDelta) commutator = maxDelta;

			float delta = commutator / 1000f;
			commutator = 0;
			
			for(Body body: bodies.toArray(new Body[bodies.size()])) body.update(delta);
			
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
	public boolean onScale(ScaleGestureDetector p1) {
		scale *= p1.getScaleFactor();
		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector p1) {
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector p1) {
		
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
	
	public class OpenTask extends AsyncTask {

		private File file;

		public OpenTask(File file) {
			this.file = file;
		}

		@Override
		protected void onPreExecute() {
			bodies.clear();

			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object[] p1) {
			try {
				center();
				InputStream stream = new FileInputStream(file);

				byte[] buffer = new byte[FORMAT_SIGN.length];
				stream.read(buffer);
				for(int i = 0; i < buffer.length; i++)
					if(buffer[i] != FORMAT_SIGN[i])
						throw new IOException("Invalid file format");
				
				byte[] intBuffer = new byte[4];
				stream.read(intBuffer);
				int count = ByteBuffer.wrap(intBuffer).getInt();
				for(int i = 0; i < count; i++) {
					stream.read(intBuffer);
					int bytes = ByteBuffer.wrap(intBuffer).getInt();

					buffer = new byte[bytes];
					stream.read(buffer);

					bodies.add(Body.parse(buffer, bodies));
				}

				stream.close();
				
				return null;
			} catch (Exception e) {
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			if(result != null) Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}
	}
	
	public class SaveTask extends AsyncTask {
		
		private File file;
		
		public SaveTask(File file) {
			this.file = file;
		}

		@Override
		protected Object doInBackground(Object[] p1) {
			try {
				OutputStream stream = new FileOutputStream(file);

				byte[] buffer;
				ByteBuffer fancyBuffer = ByteBuffer.allocate(4 + FORMAT_SIGN.length);
				fancyBuffer.put(FORMAT_SIGN);
				fancyBuffer.putInt(bodies.size());
				stream.write(fancyBuffer.array());

				fancyBuffer = ByteBuffer.allocate(4);

				for(Body body: bodies) {
					if(Thread.interrupted()) break;
					buffer = body.toByteArray(bodies);
					fancyBuffer.putInt(buffer.length);
					stream.write(fancyBuffer.array());
					stream.write(buffer);
					fancyBuffer.clear();
				}

				stream.close();
				
				return null;
			} catch (Exception e) {
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			if(result != null) Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}
	}
}
