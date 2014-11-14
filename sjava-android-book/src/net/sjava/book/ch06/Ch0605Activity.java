package net.sjava.book.ch06;

import net.sjava.book.AbstractActivity;
import net.sjava.book.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

public class Ch0605Activity extends AbstractActivity {
	private TextView mTextView;
	
	private Button mBtn01;
	private Button mBtn02;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ch06_05);
		mTextView = (TextView)findViewById(R.id.activity_ch06_05_txtview);
		mBtn01 = (Button)findViewById(R.id.activity_ch06_05_btn_01);
		mBtn02 = (Button)findViewById(R.id.activity_ch06_05_btn_02);
		
		mBtn01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new ExampleAsyncTask().execute("");
			}
		});
		
		mBtn02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ExampleAsyncTask exTask = new ExampleAsyncTask();
				exTask.execute("");
				
				try {
					Thread.sleep(2*1000);
					exTask.cancel(false); // Task를 취소한다. 
				} catch(InterruptedException e) {
					Log.e(CNAME, "에러", e);
				}
			}
		});
	}

	
public class ExampleAsyncTask extends AsyncTask<String, Integer, String> {
	@Override
    protected void onPreExecute() {
		Log.d(CNAME, Thread.currentThread().getName() +  " 스레드 onPreExecute ");
    }
	
	@Override
	protected String doInBackground(String... params) {
		Log.d(CNAME, Thread.currentThread().getName() + " 스레드 doInBackground ");
		
		for (int i = 0; i < 5; i++) {
			try {
				publishProgress(i);
				Thread.sleep(1000);
				if(isCancelled())
					return "취소됨";
					
			} catch (InterruptedException e) {
				Log.e(CNAME, "에러", e);
			}
		}

		return "실행완료";
	}

	@Override
    protected void onProgressUpdate(Integer... values) {
		mTextView.setText(values[0] + " 차례");
		
		Log.d(CNAME, Thread.currentThread().getName() +  " 스레드 onProgressUpdate ");
    }
	
	 
	@Override
	protected void onCancelled() {
		Log.d(CNAME, Thread.currentThread().getName() +  " 스레드 onCancelled ");
	}
	 
	
	@Override
    protected void onPostExecute(String result) {
		Log.d(CNAME, Thread.currentThread().getName() +  " 스레드 onPostExecute 결과 : " + result);
    }
	
	@Override
	protected void onCancelled(String result) {
		Log.d(CNAME, Thread.currentThread().getName() +  " 스레드 onCancelled 결과 : " + result);
	}
}
	

}
