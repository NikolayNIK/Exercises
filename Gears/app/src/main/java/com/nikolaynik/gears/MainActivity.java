package com.nikolaynik.gears;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;

public class MainActivity extends Activity {
	
	GearsView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = (GearsView)findViewById(R.id.gears);
		
		findViewById(R.id.buttonUp).setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View p1) {
				view.setSpeed(GearsView.SPEED_MAXIMUM);
				return true;
			}
		});
	
		findViewById(R.id.buttonReverse).setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View p1) {
				view.setSpeed(5);
				return true;
			}
		});
		
		findViewById(R.id.buttonDown).setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View p1) {
				view.setSpeed(GearsView.SPEED_MINIMUM);
				return true;
			}
		});
	}
	
	public void onClickBtnDown(View v) {
		view.setSpeed(view.getSpeed() / 2);
	}

	public void onClickBtnUp(View v) {
		view.setSpeed(view.getSpeed() * 2);
	}

	public void onClickBtnReverse(View v) {
		view.setSpeed(-view.getSpeed());
	}
}
