package com.nikolaynik.universe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.io.File;

public class OpenActivity extends Activity implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {

	ProgressBar progresd;
	ListView list;
	FileAdapter adapter;
	File dir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new FileAdapter(this);
		adapter.setNotifyOnChange(false);
		
		list = new ListView(this);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);
		
		dir = Environment.getExternalStorageDirectory();
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

	@Override
	public void onBackPressed() {
		if(dir.getParentFile() != null) {
			new OpenTask(dir.getParentFile()).execute();
		} else {
			setResult(RESULT_CANCELED);
			super.onBackPressed();
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
			
			for(File file: files) if(!file.isFile() || (file.getName().contains(".") && file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("unv"))) adapter.add(file);
			return true;
		}

		@Override
		protected void onPostExecute(Object result) {
			if((boolean)result) {
				adapter.sort();
				setContentView(list);
			} else {
				setResult(RESULT_CANCELED);
				finish();
			}
			
			super.onPostExecute(result);
		}
	}
}
