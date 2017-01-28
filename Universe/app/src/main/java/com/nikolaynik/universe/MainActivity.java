package com.nikolaynik.universe;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends ListActivity implements AdapterView.OnItemLongClickListener {
	
	public static final int REQUEST_OPEN = 69;
	public static final int REQUEST_SAVE = 6969;
	
	private File lastest;
	private BodyAdapter adapter;
	private UniverseView view;
	private SlidingDrawer drawer;
	private int selected = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		this.drawer = (SlidingDrawer)findViewById(R.id.drawer);
		this.view = (UniverseView)findViewById(R.id.theuniverse);
		super.setListAdapter(adapter = new BodyAdapter());
		super.getListView().setOnItemLongClickListener(this);
		
		lastest = new File(getExternalCacheDir().getParentFile(), "lastest.unv");
		if(lastest.isFile()) view.open(lastest);
	}

	private AlertDialog getNewRelativeDialog() {
		final View view = getLayoutInflater().inflate(R.layout.dialog_relative, null);
		
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setView(view);
		adb.setTitle(R.string.dialog_new_relative_title);
		adb.setNegativeButton(android.R.string.cancel, null);
		adb.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface p1, int p2) {
				try {
					// Creating a new body
					// Just do not look here
					MainActivity.this.view.getBodies().add(new RelativeBody(((EditText)view.findViewById(R.id.edit_name)).getText().toString(), MainActivity.this.view.getBodies().get(selected), Float.valueOf(((EditText)view.findViewById(R.id.edit_distance)).getText().toString()), Float.valueOf(((EditText)view.findViewById(R.id.edit_speed)).getText().toString()), 0, Float.valueOf(((EditText)view.findViewById(R.id.edit_size)).getText().toString()), Color.parseColor(((EditText)view.findViewById(R.id.edit_color)).getText().toString())));
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, getString(R.string.toast_error_creating_body) + ' ' + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		return adb.create();
	}

	private AlertDialog getNewAbsoluteDialog() {
		final View view = getLayoutInflater().inflate(R.layout.dialog_absolute, null);
		
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setView(view);
		adb.setTitle(R.string.dialog_new_absolute_title);
		adb.setNegativeButton(android.R.string.cancel, null);
		adb.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface p1, int p2) {
				try {
					// Creating a new body
					// Just do not look here
					MainActivity.this.view.getBodies().add(new AbsoluteBody(((EditText)view.findViewById(R.id.edit_name)).getText().toString(), Float.valueOf(((EditText)view.findViewById(R.id.edit_x)).getText().toString()), Float.valueOf(((EditText)view.findViewById(R.id.edit_y)).getText().toString()), Float.valueOf(((EditText)view.findViewById(R.id.edit_size)).getText().toString()), Color.parseColor(((EditText)view.findViewById(R.id.edit_color)).getText().toString())));
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, getString(R.string.toast_error_creating_body) + ' ' + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		return adb.create();
	}
	
	private AlertDialog getEditDialog(Body body) {
		switch(body.getType()) {
			case Body.TYPE_ABSOLUTE:
				return getEditAbsoluteDialog((AbsoluteBody)body);
			case Body.TYPE_RELATIVE:
				return getEditRelativeDialog((RelativeBody)body);
			default:
				return null;
		}
	}
	
	private AlertDialog getEditRelativeDialog(final RelativeBody body) {
		final View view = getLayoutInflater().inflate(R.layout.dialog_relative, null);
		((EditText)view.findViewById(R.id.edit_name)).setText(body.getName());
		((EditText)view.findViewById(R.id.edit_speed)).setText(Float.toString(body.getSpeed()));
		((EditText)view.findViewById(R.id.edit_distance)).setText(Float.toString(body.getDistance()));
		((EditText)view.findViewById(R.id.edit_size)).setText(Float.toString(body.getSize()));
		((EditText)view.findViewById(R.id.edit_color)).setText(String.format("#%06X", (0xFFFFFF & body.getPaint().getColor())));
		
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setView(view);
		adb.setTitle(R.string.dialog_edit_relative_title);
		adb.setNegativeButton(android.R.string.cancel, null);
		adb.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface p1, int p2) {
				try {
					body.setName(((EditText)view.findViewById(R.id.edit_name)).getText().toString());
					body.setDistance(Float.valueOf(((EditText)view.findViewById(R.id.edit_distance)).getText().toString()));
					body.setSpeed(Float.valueOf(((EditText)view.findViewById(R.id.edit_speed)).getText().toString()));
					body.setSize(Float.valueOf(((EditText)view.findViewById(R.id.edit_size)).getText().toString()));
					body.getPaint().setColor(Color.parseColor(((EditText)view.findViewById(R.id.edit_color)).getText().toString()));
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, getString(R.string.toast_error_changing_body) + ' ' + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});

		return adb.create();
	}

	private AlertDialog getEditAbsoluteDialog(final AbsoluteBody body) {
		final View view = getLayoutInflater().inflate(R.layout.dialog_absolute, null);
		((EditText)view.findViewById(R.id.edit_name)).setText(body.getName());
		((EditText)view.findViewById(R.id.edit_x)).setText(Float.toString(body.getX()));
		((EditText)view.findViewById(R.id.edit_y)).setText(Float.toString(body.getY()));
		((EditText)view.findViewById(R.id.edit_size)).setText(Float.toString(body.getSize()));
		((EditText)view.findViewById(R.id.edit_color)).setText(String.format("#%06X", (0xFFFFFF & body.getPaint().getColor())));

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setView(view);
		adb.setTitle(R.string.dialog_edit_relative_title);
		adb.setNegativeButton(android.R.string.cancel, null);
		adb.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface p1, int p2) {
				try {
					body.setName(((EditText)view.findViewById(R.id.edit_name)).getText().toString());
					body.setX(Float.valueOf(((EditText)view.findViewById(R.id.edit_x)).getText().toString()));
					body.setY(Float.valueOf(((EditText)view.findViewById(R.id.edit_y)).getText().toString()));
					body.setSize(Float.valueOf(((EditText)view.findViewById(R.id.edit_size)).getText().toString()));
					body.getPaint().setColor(Color.parseColor(((EditText)view.findViewById(R.id.edit_color)).getText().toString()));
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, getString(R.string.toast_error_changing_body) + ' ' + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});

		return adb.create();
	}
	
	public PopupMenu getBodyPopup(View view, final Body body) {
		PopupMenu menu = new PopupMenu(this, view);
		menu.inflate(R.menu.popup_body);
		menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem p1) {
				switch(p1.getItemId()) {
					case R.id.item_edit:
						getEditDialog(body).show();
						return true;
					case R.id.item_remove:
						adapter.remove(body);
						selected = -1;
						return true;
					default:
						return false;
				}
			}
		});
		return menu;
	}

	@Override
	protected void onPause() {
		view.save(lastest);
		super.onPause();
	}
	
	public void open() {
		if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_OPEN);
		else startActivityForResult(new Intent(this, OpenActivity.class), REQUEST_OPEN);
	}
	
	public void save() {
		if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_SAVE);
		else startActivityForResult(new Intent(this,SaveActivity.class), REQUEST_SAVE);
	}

	@Override
	public void onBackPressed() {
		if(drawer.isOpened()) drawer.animateClose();
		else super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		this.selected = position;
		super.onListItemClick(l, v, position, id);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {
		this.getBodyPopup(p2, adapter.getItem(p3)).show();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.item_open:
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_OPEN);
				return true;
			case R.id.item_save:
				requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_SAVE);
				return true;
			case R.id.item_center:
				view.center();
				return true;
			case R.id.item_new_absolute:
				this.getNewAbsoluteDialog().show();
				return true;
			case R.id.item_new_relative:
				this.getNewRelativeDialog().show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			File file = new File(Uri.decode(data.getDataString()).replace("file://", ""));
			switch(requestCode) {
				case REQUEST_OPEN:
					view.open(file);
					break;
				case REQUEST_SAVE:
					view.save(file);
					break;
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			switch(requestCode) {
				case REQUEST_OPEN:
					open();
					break;
				case REQUEST_SAVE:
					save();
					break;
			}
		}
		
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
	
	public class BodyAdapter extends ArrayAdapter<Body> {
		
		public BodyAdapter() {
			super(MainActivity.this, android.R.layout.simple_list_item_1, view.getBodies());
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if(view == null) view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
			
			((TextView)view).setText(getItem(position).getName());
			
			return view;
		}
	}
}
