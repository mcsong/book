package net.sjava.book.ch06;

import net.sjava.book.AbstractActivity;
import net.sjava.book.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ch0604Activity extends AbstractActivity {
	private TextView mTextView;
	private Button mButton01;

	final HandlerThread hThread = new DummyHandlerThread("HandlerThread.Handler");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ch06_04);
		mTextView = (TextView)findViewById(R.id.activity_ch06_04_txtview);
		mButton01 = (Button)findViewById(R.id.activity_ch06_04_btn_01);
		
		mButton01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Handler h = new Handler(hThread.getLooper()) {
					// HandlerThread에서 처리한다. 
					public void handleMessage(Message msg) {
						Log.d(CNAME, Thread.currentThread().getName());
						Log.d(CNAME, "처리 완료");
						
						// UI 스레드에게 전달한다. 
						new Handler(Looper.getMainLooper()).post(new UpdateRunnable());
					}
				};
				
				h.sendMessage(new Message());
			}
		});
		
		hThread.start();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(hThread != null)
			hThread.quit();
	}
	
	class UpdateRunnable implements Runnable{
		@Override
		public void run() {
			mTextView.setText("UI 업데이트 완료");
		}
	}
	
	class DummyHandlerThread extends HandlerThread {
		public DummyHandlerThread(String name) {
			super(name);
		}
		
		@Override
		public void onLooperPrepared() {
			Log.d(CNAME, "Looper is ready");
		}
	}
	
}
