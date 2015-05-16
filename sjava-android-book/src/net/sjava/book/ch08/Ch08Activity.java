package net.sjava.book.ch08;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
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
	private Button mBtn06;
	private Button mBtn07;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch08);
		
		mBtn01 = (Button) findViewById(R.id.activity_ch08_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch08_btn_02);
		mBtn03 = (Button) findViewById(R.id.activity_ch08_btn_03);
		mBtn04 = (Button) findViewById(R.id.activity_ch08_btn_04);
		mBtn05 = (Button) findViewById(R.id.activity_ch08_btn_05);
		mBtn06 = (Button) findViewById(R.id.activity_ch08_btn_06);
		mBtn07 = (Button) findViewById(R.id.activity_ch08_btn_07);
		
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
		
		mBtn06.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialog pd = new ProgressDialog(v.getContext());
				pd.setTitle("");		
				pd.setMessage("Loading...");
				pd.setCancelable(true);
				
				LoadingTask task = new LoadingTask(pd);
				// 1초를 주기로 10초 동안 스레드 실행을 허용
				new AsyncTaskCancelTimer(task, 10000, 1000, true).start();
				task.execute("");
			}
		});
		
		mBtn07.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialog pd = new ProgressDialog(v.getContext());
				pd.setTitle("");		
				pd.setMessage("Loading...");
				pd.setCancelable(true);
				
				LoadingTask task = new LoadingTask(pd);
				// 1초를 주기로 10초 동안 스레드 실행을 허용
				new AsyncTaskCancelTimer(task, 10000, 1000, true).start();
				task.execute("");
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
	
	
	@SuppressWarnings("rawtypes")
	static class AsyncTaskCancelTimer extends CountDownTimer {
		private AsyncTask asyncTask;
		private boolean interrupt;
		
		private AsyncTaskCancelTimer(AsyncTask asyncTask, long startTime, long interval) {
			super(startTime, interval);
			this.asyncTask = asyncTask;
		}
				
		private AsyncTaskCancelTimer(AsyncTask asyncTask, long startTime, long interval, boolean interrupt) {
			super(startTime, interval);
			this.asyncTask = asyncTask;
			this.interrupt = interrupt;
		}
		
		@Override
		public void onTick(long millisUntilFinished) {
			Log.d("AsyncTaskCancelTimer", "onTicked at " + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()) );
			
			if(asyncTask == null) {
				this.cancel();
				return;
			}
						
			if(asyncTask.isCancelled())
				this.cancel();
			
			if(asyncTask.getStatus() == AsyncTask.Status.FINISHED)
			    this.cancel();
		}

		@Override
		public void onFinish() {
			Log.d("AsyncTaskCancelTimer", "onTick..");
			
			if(asyncTask == null || asyncTask.isCancelled() )
				return;
			
			try {
				if(asyncTask.getStatus() == AsyncTask.Status.FINISHED)
					return;
				
				if(asyncTask.getStatus() == AsyncTask.Status.PENDING || 
					asyncTask.getStatus() == AsyncTask.Status.RUNNING ) {
					
					asyncTask.cancel(interrupt);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	static class LoadingTask extends AsyncTask<String, Integer, Boolean> {
		private ProgressDialog pd = null;
				
		public LoadingTask(ProgressDialog pd) {
			this.pd = pd;
		}
		
		@Override
		protected void onPreExecute() {	
			super.onPreExecute();
			if(pd != null)
				pd.show();
			
			Log.d("LoadingTask", "onPreExecute..");
		}
		
		@Override
		protected Boolean doInBackground(String... params) {			
			try {
				Thread.sleep(1000 * 20);
			} catch(InterruptedException e) {
				Log.d("LoadingTask", "Exception : " + e.getLocalizedMessage());
			}
			
			return Boolean.TRUE;
		}

		@Override
		protected void onCancelled(Boolean result) {
			Log.d("LoadingTask", "onCancelled : " + result);
			
			if(pd != null)
				pd.dismiss();
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			Log.d("LoadingTask", "onPostExecute : " + result);
			
			if(pd != null)
				pd.dismiss();
		}
	}
	
}
