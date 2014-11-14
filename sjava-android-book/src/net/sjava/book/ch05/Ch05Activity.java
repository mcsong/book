package net.sjava.book.ch05;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import net.sjava.book.AbstractActivity;
import net.sjava.book.R;
import net.sjava.book.ch05.callable.CallableWorker;
import net.sjava.book.ch05.executor.ThreadPoolMonitor;
import net.sjava.book.ch05.executor.ThreadPoolRejectHandler;
import net.sjava.book.ch05.executor.ThreadPoolWorker;
import net.sjava.book.ch05.future.FutureTaskWorker;
import net.sjava.book.ch05.future.FutureWorker;


public class Ch05Activity extends AbstractActivity {
	private Button mBtn01;
	private Button mBtn02;
	private Button mBtn03;
	private Button mBtn0401;
	private Button mBtn0402;
	private Button mBtn05;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch05);
		
		mBtn01 = (Button) findViewById(R.id.activity_ch05_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch05_btn_02);
		mBtn03 = (Button) findViewById(R.id.activity_ch05_btn_03);
		mBtn0401 = (Button) findViewById(R.id.activity_ch05_btn_04_01);
		mBtn0402 = (Button) findViewById(R.id.activity_ch05_btn_04_02);
		mBtn05 = (Button) findViewById(R.id.activity_ch05_btn_05);
				
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ThreadTest t1 = new ThreadTest();
				t1.setPriority(Thread.NORM_PRIORITY);
				t1.setName("스레드1");
				t1.start();

				ThreadTest t2 = new ThreadTest();
				t2.setPriority(Thread.NORM_PRIORITY + 1);
				
				t2.setName("스레드2");
				t2.start();
			}
		});
		
		
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread t1 = new Thread(new RunnableTest());
				t1.setPriority(Thread.NORM_PRIORITY);
				t1.setName("Runnable 스레드1");
				t1.start();

				Thread t2 = new Thread(new RunnableTest());
				t2.setPriority(Thread.NORM_PRIORITY + 1);
				t2.setName("Runnable 스레드2");
				t2.start();
			}
		});
		
		mBtn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> arrays = new ArrayList<String>();
				arrays.add("111");
				arrays.add("222");
				arrays.add("333");
				arrays.add("444");
				arrays.add("555");

				ExecutorService executor = Executors.newFixedThreadPool(2);
				ArrayList<Future<String>> rvalues = new ArrayList<Future<String>>();

				try {
					for (String value : arrays)
						rvalues.add(executor.submit(new CallableWorker(value)));

					for (Future<String> result : rvalues)
						Log.d(CNAME, result.get());
					
				} catch (Exception e) {
					Log.e(CNAME, "에러", e);
				}

				executor.shutdown();
			}
		});
		
		
		mBtn0401.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ExecutorService executor = Executors.newFixedThreadPool(2);
				Future<String> f1 = executor.submit(new FutureWorker(1000));
				Future<String> f2 = executor.submit(new FutureWorker(1000));
				Future<String> f3 = executor.submit(new FutureWorker(1000));
				
				executor.shutdown();
				try{
					Log.d(CNAME, "f1 isDone : "+ f1.isDone()+" return value : " + f1.get());
					Log.d(CNAME, "f2 isDone : "+ f2.isDone()+" return value : " + f2.get() );
					
					f3.get(1, TimeUnit.SECONDS);
				} catch(Exception e) {
					Log.e(CNAME, "에러", e);
				}	
			}
		});
		
		mBtn0402.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ExecutorService executor = Executors.newFixedThreadPool(2);
				
				FutureTask<Integer> f1 = new FutureTask<Integer>(new FutureTaskWorker(true, 1000));
				FutureTask<Integer> f2 = new FutureTask<Integer>(new FutureTaskWorker(false, 1000));
				FutureTask<Integer> f3 = new FutureTask<Integer>(new FutureTaskWorker(false, 1000));
				
				executor.submit(f1);
				executor.submit(f2);
				executor.submit(f3);
				
				executor.shutdown();
				try{
					
					Log.d(CNAME, "f1 isDone : "+ f1.isDone());
					while(!f1.isDone()) {
						Thread.sleep(100);
					}
					
					Log.d(CNAME, "f1 isDone : "+ f1.isDone()+" return value : " + f1.get() );
					Log.d(CNAME, "f2 isDone : "+ f2.isDone()+" return value : " + f2.get() );
					

					f3.get(1, TimeUnit.SECONDS);
				} catch(Exception e) {
					Log.e(CNAME, "에러", e);
					e.printStackTrace();
				} finally {
					executor.shutdown();
				}
			}
		});
		
		
		mBtn05.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ThreadPoolExecutor를 생성한다.
				ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5,
					10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2), new ThreadPoolRejectHandler());

				// ThreadPoolExecutor의 상태를 확인하는 스레드를 생성한다.
				ThreadPoolMonitor threadPoolMonitor = new ThreadPoolMonitor(threadPoolExecutor);
				new Thread(threadPoolMonitor).start();

				for (int i = 0; i < 10; i++)
					threadPoolExecutor.execute(new ThreadPoolWorker(i +" 스레드"));

				try {
					Thread.sleep(10 * 1000);
					threadPoolExecutor.shutdown();
	
					Thread.sleep(3 * 1000);
					threadPoolMonitor.shutdown();
				} catch(Exception e) {
					Log.e(CNAME, "에러", e);
				}
			}
		
		});
		
	}
}
