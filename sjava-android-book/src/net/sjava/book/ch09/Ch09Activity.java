package net.sjava.book.ch09;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import net.sjava.book.AbstractActivity;
import net.sjava.book.R;

public class Ch09Activity extends AbstractActivity {
	
	private Button mBtn01;
	private Button mBtn02;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch09);
		
		mBtn01 = (Button) findViewById(R.id.activity_ch09_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch09_btn_02);

		
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch0901Activity.class);
				startActivity(intent);
			}
		});
		
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch0902Activity.class);
				startActivity(intent);
			}

		});
		

	}
	

	

}
