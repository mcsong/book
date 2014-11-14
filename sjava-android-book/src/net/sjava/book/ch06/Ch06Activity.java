package net.sjava.book.ch06;

import net.sjava.book.AbstractActivity;
import net.sjava.book.BitmapHandler;
import net.sjava.book.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Ch06Activity extends AbstractActivity {
	
	private Button mBtn01;
	private Button mBtn02;
	private Button mBtn03;
	private Button mBtn04;
	private Button mBtn05;
	private Button mBtn06;
	
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch06);
		
		mBtn01 = (Button) findViewById(R.id.activity_ch06_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch06_btn_02);
		mBtn03 = (Button) findViewById(R.id.activity_ch06_btn_03);
		mBtn04 = (Button) findViewById(R.id.activity_ch06_btn_04);
		mBtn05 = (Button) findViewById(R.id.activity_ch06_btn_05);
		mBtn06 = (Button) findViewById(R.id.activity_ch06_btn_06);
		
		initizeActions();
	}
	
	private void initizeActions() {
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx(), Ch0601Activity.class);
				startActivity(i);
				
			}
		});
		
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx(), Ch0602Activity.class);
				startActivity(i);
				
			}
		});
		
		mHandler = new Handler(new Handler.Callback() {	
			@Override
			public boolean handleMessage(Message msg) {
				try{
					Log.d(CNAME, " Handler handleMessage() ½º·¹µå : " + Thread.currentThread().getName());
					//mImageView.setImageBitmap(BitmapHandler.newInstance().load("android_01.png"));
				} catch(Throwable e) {
					Log.e(CNAME, "handleMessage Error", e);
				}
				return false;
			}
		});

		mBtn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch0603Activity.class);
				startActivity(intent);
			}
		});

		mBtn04.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch0604Activity.class);
				startActivity(intent);
			}
		});
		
		mBtn05.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch0605Activity.class);
				startActivity(intent);
			}
		});

		mBtn06.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch0606Activity.class);
				startActivity(intent);
			}
		});
	}

}
