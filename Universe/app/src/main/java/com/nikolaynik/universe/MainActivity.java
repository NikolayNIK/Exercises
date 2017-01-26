package com.nikolaynik.universe;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;

public class MainActivity extends ListActivity implements AdapterView.OnItemLongClickListener {
	
	private BodyAdapter adapter;
	private UniverseView view;
	private int selected = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		this.view = (UniverseView)findViewById(R.id.theuniverse);
		super.setListAdapter(adapter = new BodyAdapter());
		super.getListView().setOnItemLongClickListener(this);
	}

	private AlertDialog getNewRelativeDialog() {
		final View view = getLayoutInflater().inflate(R.layout.dialog_new_relative, null);
		
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
					MainActivity.this.view.getBodies().add(new RelativeBody(((EditText)view.findViewById(R.id.edit_name)).getText().toString(), MainActivity.this.view.getBodies().get(selected), Float.valueOf(((EditText)view.findViewById(R.id.edit_distance)).getText().toString()), Float.valueOf(((EditText)view.findViewById(R.id.edit_speed)).getText().toString()), Float.valueOf(((EditText)view.findViewById(R.id.edit_size)).getText().toString()), Color.parseColor(((EditText)view.findViewById(R.id.edit_color)).getText().toString())));
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, getString(R.string.toast_error_creating_body) + ' ' + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		return adb.create();
	}

	private AlertDialog getNewAbsoluteDialog() {
		final View view = getLayoutInflater().inflate(R.layout.dialog_new_absolute, null);
		
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
		this.adapter.remove(adapter.getItem(p3));
		this.selected = -1;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
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
