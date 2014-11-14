package net.sjava.book.ch06;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.sjava.book.R;


public class Ch0606Adapter extends ArrayAdapter<String> {
	private final LayoutInflater inflater;
	
	public Ch0606Adapter(Context ctx, int resource) {
		super(ctx, resource);
		this.inflater = LayoutInflater.from(ctx);
	}
	  
	public Ch0606Adapter(Context ctx, int resource, List<String> strArray) {
		super(ctx, resource, strArray);	
		this.inflater = LayoutInflater.from(ctx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null)
			view = inflater.inflate(R.layout.activity_ch06_06_item, parent, false);
		else
			view = convertView;
		
		((TextView) view.findViewById(R.id.activity_ch06_05_item_textview)).setText(getItem(position));		
		return view;
	}
}
