package com.nikolaynik.universe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.io.File;
import java.util.Comparator;

public class FileAdapter extends ArrayAdapter<File> implements Comparator<File> {
	
	public FileAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view == null) view = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(android.R.layout.simple_list_item_1, null);
		
		((TextView)view).setText(getItem(position).getName());
		
		return view;
	}

	public void sort() {
		super.sort(this);
	}
	
	@Override
	public int compare(File p1, File p2) {
		if(p1.isFile()) {
			if(p2.isFile()) {
				return p1.getName().toLowerCase().compareTo(p2.getName().toLowerCase());
			} else {
				return 1;
			}
		} else {
			if(p2.isFile()) {
				return -1;
			} else {
				return p1.getName().toLowerCase().compareTo(p2.getName().toLowerCase());
			}
		}
	}
}
