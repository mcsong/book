package net.sjava.book.ch04;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import net.sjava.book.AbstractActivity;
import net.sjava.book.BitmapHandler;
import net.sjava.book.BookApplication;
import net.sjava.book.R;

public class Ch0401Activity extends AbstractActivity {
	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch04_01);
		
		mTextView = (TextView)findViewById(R.id.activity_ch04_01_txtview);
		
		// 메인 스레드 실행
		

		runUIThread();
		//runNonUIThread();
		
		// 일반 스레드로 실행
		/*

		*/		
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
					mTextView.setText("초기화 완료");
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
							mTextView.setText("초기화 완료");
						}
					});
				}catch(Throwable e) {
					Log.e(CNAME, "runOnUiThread error", e);
				}				
			}
		}).start();
	}
}
