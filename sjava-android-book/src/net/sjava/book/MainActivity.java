package net.sjava.book;

import java.io.File;





import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import net.sjava.book.R;
import net.sjava.book.ch03.Ch03Activity;
import net.sjava.book.ch04.Ch04Activity;
import net.sjava.book.ch05.Ch05Activity;
import net.sjava.book.ch06.Ch06Activity;
import net.sjava.book.ch07.BackgroundWorker;
import net.sjava.book.ch07.ForegroundWorker;
import net.sjava.book.ch08.Ch08Activity;
import net.sjava.book.ch09.Ch0901Activity;

public class MainActivity extends AbstractActivity {
	private Button m03Button;
	private Button m04Button;
	private Button m05Button;
	private Button m06Button;
	private Button m07Button;
	private Button m08Button;
	private Button m09Button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		
		m03Button = (Button)findViewById(R.id.activity_main_ch03_btn);
		m04Button = (Button)findViewById(R.id.activity_main_ch04_btn);
		m04Button.setEnabled(false);
		m05Button = (Button)findViewById(R.id.activity_main_ch05_btn);
		m06Button = (Button)findViewById(R.id.activity_main_ch06_btn);
		m07Button = (Button)findViewById(R.id.activity_main_ch07_btn);
		m07Button.setEnabled(false);
		m08Button = (Button)findViewById(R.id.activity_main_ch08_btn);
		m09Button = (Button)findViewById(R.id.activity_main_ch09_btn);
		
		Log.d(CNAME, "UI Thread Prioriry : " + Process.getThreadPriority(Process.myTid()));
		
		m03Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch03Activity.class);
				startActivity(intent);
			}
		});

		m04Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch04Activity.class);
				startActivity(intent);
			}
		});
		
		m05Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch05Activity.class);
				startActivity(intent);
			}
		});

		m06Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch06Activity.class);
				startActivity(intent);
				/*
				for(int i=0; i < 20; i++) {
					if(i == 10 || i == 19)
						new ForegroundWorker().start();
					else
						new BackgroundWorker().start();
				}
				*/
			}
		});
		

		
		m08Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch08Activity.class);
				startActivity(intent);
			}
		});
		
		
		m09Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx(), Ch0901Activity.class);
				startActivity(intent);
			}
		});
				
	}
	

	
	class InitializeThread extends Thread implements Runnable {
		private Context ctx;
		private TextView tv;
		public InitializeThread(Context ctx, TextView tv) {
			this.ctx = ctx;
			this.tv = tv;
		}
		
		@Override
		public void run() {
			try {
				File f = new File(Environment.getExternalStorageDirectory() +"/net.sjava.book/");
				if(!f.exists())
					f.mkdirs();
				
				Bitmap bitmap = null;
				BitmapHandler handler = BitmapHandler.newInstance();
				for(int i=0; i < 6; i++) {
					bitmap = BitmapFactory.decodeResource(ctx.getResources(), BookApplication.imageArray[i]);
					handler.save(bitmap, BookApplication.strArray[i]);
				}
				
				// ���� �����尡 �ƴϱ� ������ ����..
				tv.setText("�ʱ�ȭ �Ϸ�");
				
			} catch(Exception e) {
				Log.e(CNAME, "run error", e);
				e.printStackTrace();
			}
		}
	}
	
	
	
}
