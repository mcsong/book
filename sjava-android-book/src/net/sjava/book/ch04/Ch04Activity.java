package net.sjava.book.ch04;

import net.sjava.book.AbstractActivity;
import net.sjava.book.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Ch04Activity extends AbstractActivity {	
	private Button mBtn01;
	private Button mBtn02;

	private Intent service;
	private static int currentValue;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch04);
		
		mBtn01 = (Button) findViewById(R.id.activity_ch04_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch04_btn_02);
		
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				service = new Intent(getBaseContext(), Ch04Service.class);
				service.putExtra("txt", String.valueOf(++currentValue));
				startService(service);
			}
		});

		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopService(service);
			}
		});
		
	}
	
	/*
	private void initizeActions() {
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message m = new Message();
				m.obj = "Handler call message";
				mHandler.sendMessage(m);
			}
		});
		
		mHandler = new Handler(new Handler.Callback() {	
			@Override
			public boolean handleMessage(Message msg) {
				try{
					Log.d(CNAME, " Handler handleMessage() 스레드 : " + Thread.currentThread().getName());
					//mImageView.setImageBitmap(BitmapHandler.newInstance().load("android_01.png"));
				} catch(Throwable e) {
					Log.e(CNAME, "handleMessage Error", e);
				}
				return false;
			}
		});
		
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						try {
							Log.d(CNAME, "Handler post() 스레드 : " + Thread.currentThread().getName());
							Thread.sleep(6000); // UI thread blocked	
							//mImageView.setImageBitmap(BitmapHandler.newInstance().load("android_02.png"));
							
						} catch(Exception e) {
							Log.e(CNAME, "post Error", e);
						}
					}
				});
			}
		});
		
		mBtn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Ch04Activity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(ctx(), Ch0401Activity.class);
						startActivity(intent);
					}
				});
			}
		});
		
	}
	*/

}
