package com.nikolaynik.universe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.io.File;

public class SaveActivity extends Activity implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {

	ProgressBar progresd;
	ListView list;
	View view;
	FileAdapter adapter;
	File dir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = getLayoutInflater().inflate(R.layout.activity_save, null);
		setContentView(view);

		adapter = new FileAdapter(this);
		adapter.setNotifyOnChange(false);

		list = (ListView)view.findViewById(android.R.id.list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);

		dir = Environment.getExternalStorageDirectory();
	}

	public AlertDialog getNewFolderDialog() {
		final EditText edit = new EditText(this);
		
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("New Folder");
		adb.setView(edit);
		adb.setNegativeButton(android.R.string.cancel, null);
		adb.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface p1, int p2) {
				new File(dir, edit.getText().toString()).mkdir();
				new OpenTask(dir).execute();
			}
		});
		return adb.create();
	}
	
	@Override
	protected void onResume() {
		new OpenTask(dir).execute();
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
		File file = adapter.getItem(p3);
		if(file.isFile()) {
			Intent i = new Intent();
			i.setData(Uri.fromFile(file));
			setResult(RESULT_OK, i);
			finish();
		} else {
			new OpenTask(file).execute();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {

		return false;
	}

	public void onClickBtnSelect(View view) {
		String name = ((EditText)findViewById(R.id.edit_name)).getText().toString();
		if(!name.contains(".") || !name.substring(name.lastIndexOf(".") + 1).equals("unv")) name += ".unv";
		
		Intent i = new Intent();
		i.setData(Uri.fromFile(new File(dir, name)));
		setResult(RESULT_OK, i);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		if(dir.getParentFile() != null) {
			new OpenTask(dir.getParentFile()).execute();
		} else {
			setResult(RESULT_CANCELED);
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_save, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.item_new_folder:
				getNewFolderDialog().show();
				return true;
			default:
				return false;
		}
	}

	public class OpenTask extends AsyncTask {

		private final File target;

		public OpenTask(File target) {
			this.target = target;
		}

		@Override
		protected void onPreExecute() {
			dir = target;
			getActionBar().setSubtitle(dir.getAbsolutePath());
			setContentView(R.layout.activity_progress);
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object[] p1) {
			adapter.clear();
			File[] files = dir.listFiles();
			if(files == null) return false;

			for(File file: files) if(!file.isFile() || file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("unv")) adapter.add(file);
			return true;
		}

		@Override
		protected void onPostExecute(Object result) {
			if((boolean)result) {
				adapter.sort();
				setContentView(view);
			} else {
				setResult(RESULT_CANCELED);
				finish();
			}

			super.onPostExecute(result);
		}
	}
}
