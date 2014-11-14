package net.sjava.book.ch03;

import java.io.File;

import net.sjava.book.AbstractActivity;
import net.sjava.book.BitmapHandler;
import net.sjava.book.BookApplication;
import net.sjava.book.R;
import net.sjava.book.ch06.Ch0601Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Ch03Activity extends AbstractActivity {
	private Button mBtn01;
	private Button mBtn02;
	private Button mBtn03;
	private Button mBtn04;

	private Intent service;
	private static int currentValue;
	private TextView mTxtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch03);

		mBtn01 = (Button) findViewById(R.id.activity_ch03_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch03_btn_02);
		mBtn03 = (Button) findViewById(R.id.activity_ch03_btn_03);
		
		mTxtView = (TextView) findViewById(R.id.activity_ch03_txtview_01);
		//mBtn03 = (Button) findViewById(R.id.activity_ch03_btn_03);

		// mBtn03 = (Button) findViewById(R.id.activity_ch02_btn_03);
		// mBtn04 = (Button) findViewById(R.id.activity_ch02_btn_04);
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx(), Ch0301Activity.class);
				startActivity(i);
				
				//service = new Intent(getBaseContext(), Ch03Service.class);
				//service.putExtra("txt", String.valueOf(++currentValue));
				//startService(service);
			}
		});
		
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new UpdateThread().start();
				
				//service = new Intent(getBaseContext(), Ch03Service.class);
				//service.putExtra("txt", String.valueOf(++currentValue));
				//startService(service);
			}
		});
		
		mBtn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						File f = new File(Environment.getExternalStorageDirectory() +"/net.sjava.book/");
						if(!f.exists())
							f.mkdirs();
						
						Bitmap bitmap = null;
						BitmapHandler handler = BitmapHandler.newInstance();
						try {
							
							// resource에 있는 이미지를 로딩하고 이 이미지를 UI 스레드에서 저장한다. 
							for(int i=0; i < 6; i++) {
								bitmap = BitmapFactory.decodeResource(getResources(), BookApplication.imageArray[i]);
								handler.save(bitmap, BookApplication.strArray[i]);
								Thread.sleep(1000);
							}
							
							// 메인 스레드이기에 화면 갱신가능
							Log.d(CNAME, Thread.currentThread().getName());					
							mTxtView.setText("변경 완료");
						} catch(Throwable e) {
							Log.e(CNAME, "runOnUiThread error", e);
						}
					}
				});
			}
		});
		/*
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				service = new Intent(getBaseContext(), Ch03Service.class);
				service.putExtra("txt", String.valueOf(++currentValue));
				startService(service);
			}
		});

		mBtn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopService(service);
			}
		});

		IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
		BroadcastReceiver receiver = new Ch03HeadSetBroadcastReceiver();
		registerReceiver(receiver, receiverFilter);
		*/
	}
	
	
	class UpdateThread extends Thread {
		@Override
		public void run() {
			try {
				mTxtView.setText("update");
			} catch(Exception e) {
				Log.e(CNAME, "에러", e);
			}
		}
	}
	
	private void runUIThread() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				File f = new File(Environment.getExternalStorageDirectory() +"/net.sjava.book/");
				if(!f.exists())
					f.mkdirs();
				
				Bitmap bitmap = null;
				BitmapHandler handler = BitmapHandler.newInstance();
				try {
					
					// resource에 있는 이미지를 로딩하고 이 이미지를 UI 스레드에서 저장한다. 
					for(int i=0; i < 6; i++) {
						bitmap = BitmapFactory.decodeResource(getResources(), BookApplication.imageArray[i]);
						handler.save(bitmap, BookApplication.strArray[i]);
						Thread.sleep(1000);
					}
					
					// 메인 스레드이기에 화면 갱신가능
					Log.d(CNAME, Thread.currentThread().getName());					
					mTxtView.setText("변경 완료");
				} catch(Throwable e) {
					Log.e(CNAME, "runOnUiThread error", e);
				}
			}
		});
	}
	
	private void runNonUIThread() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				File f = new File(Environment.getExternalStorageDirectory() +"/net.sjava.book/");
				if(!f.exists())
					f.mkdirs();
				
				Bitmap bitmap = null;
				BitmapHandler handler = BitmapHandler.newInstance();
				
				try {
					// resource에 있는 이미지를 로딩하고 이 이미지를 UI 스레드에서 저장한다.
					for(int i=0; i < 6; i++) {
						bitmap = BitmapFactory.decodeResource(getResources(), BookApplication.imageArray[i]);
						handler.save(bitmap, BookApplication.strArray[i]);
					}
					
					Log.d(CNAME, Thread.currentThread().getName());
					
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// 메인 스레드이기에 화면 갱신가능
							Log.d(CNAME, Thread.currentThread().getName());
						}
					});
				}catch(Throwable e) {
					Log.e(CNAME, "runOnUiThread error", e);
				}				
			}
		}).start();
	}
}
