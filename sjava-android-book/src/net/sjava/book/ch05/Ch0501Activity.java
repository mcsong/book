package net.sjava.book.ch05;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import net.sjava.book.AbstractActivity;
import net.sjava.book.R;

public class Ch0501Activity extends AbstractActivity {
	
	private TextView txtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch05_01);
		
		txtView = (TextView)findViewById(R.id.activity_ch05_01_txtView_01);		
		View v = txtView.getRootView();
		
		System.out.println(v.getWidth() + ", "+ v.getHeight());
	}
}
