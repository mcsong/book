package net.sjava.book.ch08;


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

public class Ch08Activity extends AbstractActivity {
	
	private Button mBtn01;
	private Button mBtn02;
	private Button mBtn03;
	private Button mBtn04;
	private Button mBtn05;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch08);
		
		mBtn01 = (Button) findViewById(R.id.activity_ch08_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch08_btn_02);
		mBtn03 = (Button) findViewById(R.id.activity_ch08_btn_03);
		mBtn04 = (Button) findViewById(R.id.activity_ch08_btn_04);
		mBtn05 = (Button) findViewById(R.id.activity_ch08_btn_05);
		
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				for(int i=0; i < 200; i++)
					new ExampleTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
			}
		});
		
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for(int i=0; i < 200; i++) {
					new ExampleTask().executeOnExecutor(CustomThreadPoolExecutor.POOL_EXECUTOR, "");
				}
				
				
				SystemClock.sleep(2000);
				Log.d(CNAME, CustomThreadPoolExecutor.POOL_EXECUTOR.toString() );
			}
		});
		
		mBtn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for(int i=0; i < 20; i++) {
					new PriorityExampleTask(i).executeOnExecutor(PriorityQueueAsyncTask.THREAD_POOL_EXECUTOR, "");
				}
				
				SystemClock.sleep(2000);
				Log.d(CNAME, PriorityQueueAsyncTask.THREAD_POOL_EXECUTOR.toString() );
			}
		});
	
		mBtn04.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for(int i=0; i < 20; i++) {
					new LIFOExampleTask(i).executeOnExecutor(LIFOQueueAsyncTask.THREAD_POOL_EXECUTOR, "");
				}
				
				SystemClock.sleep(2000);
				Log.d(CNAME, LIFOQueueAsyncTask.THREAD_POOL_EXECUTOR.toString() );
			}
		});
		
		mBtn05.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for(int i=0; i < 20; i++) {
					if(i == 10 || i == 18)
						new PriorityThreadAExampleTask(Process.THREAD_PRIORITY_DEFAULT).executeOnExecutor(PriorityThreadAExampleTask.THREAD_POOL_EXECUTOR, "");
					else
						new PriorityThreadBExampleTask(Process.THREAD_PRIORITY_BACKGROUND).executeOnExecutor(PriorityThreadBExampleTask.THREAD_POOL_EXECUTOR, "");
				}
								
				SystemClock.sleep(3000);
				Log.d(CNAME, PriorityThreadAExampleTask.THREAD_POOL_EXECUTOR.toString() );
				Log.d(CNAME, PriorityThreadBExampleTask.THREAD_POOL_EXECUTOR.toString() );
			}
		});
	}
	

	
	static class ExampleTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			SystemClock.sleep(1000);
			return null;
		}
	}
	
	class PriorityExampleTask extends PriorityQueueAsyncTask<String, Void, String> {
		private int priority;
		public PriorityExampleTask(int priority) {
			super(priority);
			this.priority = priority;
		}

		@Override
		protected String doInBackground(String... params) {
			SystemClock.sleep(1000);
			
			Log.d("PriorityExampleTask", priority +" finished");
			return null;
		}
	}
	
	
	class LIFOExampleTask extends LIFOQueueAsyncTask<String, Void, String> {
		private int taskNumber;
		public LIFOExampleTask(int taskNumber) {
			this.taskNumber = taskNumber;
		}

		@Override
		protected String doInBackground(String... params) {
			SystemClock.sleep(1000);
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.d("LIFOExampleTask", taskNumber +" finished");
		}
	}
	
	
	class PriorityThreadAExampleTask extends PriorityThreadAAsyncTask<String, Void, String> {
		private int priority;
		public PriorityThreadAExampleTask(int priority) {
			super(priority);
			this.priority = priority;
		}

		private boolean isPrime(int number) {
			if (number % 3 == 0)
				return false;

			int y = 2;
			int x = (int) Math.sqrt(number);

			for (int i = 5; i <= x; i += y, y = 6 - y)
				if (number % i == 0)
					return false;

			return true;
		}

		@Override
		protected String doInBackground(String... params) {
			long start = SystemClock.currentThreadTimeMillis();

			int count = 0;
			for(int i = 1; i < 100000; i++) {
				if(isPrime(i))
					count++;
			}
			
			long end = SystemClock.currentThreadTimeMillis();
			
			Log.d("PriorityThreadAExampleTask", "PriorityThreadAExampleTask["+ priority+"] running time : " + (end - start) +" , prime count : " + count);
			return null;
		}
	}

	class PriorityThreadBExampleTask extends PriorityThreadBAsyncTask<String, Void, String> {
		private int priority;
		public PriorityThreadBExampleTask(int priority) {
			super(priority);
			this.priority = priority;
		}

		private boolean isPrime(int number) {
			if (number % 3 == 0)
				return false;

			int y = 2;
			int x = (int) Math.sqrt(number);

			for (int i = 5; i <= x; i += y, y = 6 - y)
				if (number % i == 0)
					return false;

			return true;
		}

		@Override
		protected String doInBackground(String... params) {
			long start = SystemClock.currentThreadTimeMillis();

			int count = 0;
			for(int i = 1; i < 100000; i++) {
				if(isPrime(i))
					count++;
			}
			
			long end = SystemClock.currentThreadTimeMillis();
			
			Log.d("PriorityThreadBExampleTask", "PriorityThreadBExampleTask["+ priority+"] running time : " + (end - start) +" , prime count : " + count);
			return null;
		}
	}
}
