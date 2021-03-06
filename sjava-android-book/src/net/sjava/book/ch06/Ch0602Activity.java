package net.sjava.book.ch06;

import java.io.File;

import net.sjava.book.AbstractActivity;
import net.sjava.book.BitmapHandler;
import net.sjava.book.BookApplication;
import net.sjava.book.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ch0602Activity extends AbstractActivity {
	private TextView mTextView;
	
	private Button mBtn01;
	private Button mBtn02;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ch06_02);
		mTextView = (TextView)findViewById(R.id.activity_ch06_02_txtview);
		mBtn01 = (Button)findViewById(R.id.activity_ch06_02_btn_01);
		mBtn02 = (Button)findViewById(R.id.activity_ch06_02_btn_02);
		
		mBtn01.setOnClickListener(new View.OnClickListener() {
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
							for(int i=0; i < 6; i++) {
								bitmap = BitmapFactory.decodeResource(getResources(), BookApplication.imageArray[i]);
								handler.save(bitmap, BookApplication.strArray[i]);
							}
							
							// 메인 스레드이기에 화면 갱신가능
							Log.d(CNAME, Thread.currentThread().getName());					
							mTextView.setText("예제 6.6 변경 완료");
						} catch(Throwable e) {
							Log.e(CNAME, "runOnUiThread error", e);
						}
					}
				});
			}
		});
		
		mBtn02.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						File f = new File(Environment.getExternalStorageDirectory() +"/net.sjava.book/");
						if(!f.exists())
							f.mkdirs();
						
						Bitmap bitmap = null;
						BitmapHandler handler = BitmapHandler.newInstance();
						
						try {
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
									mTextView.setText("예제 6-7 변경 완료");
								}
							});
						}catch(Throwable e) {
							Log.e(CNAME, "runOnUiThread error", e);
						}				
					}
				}).start();
				
			}
			
		});


	}

	
	
	

}
