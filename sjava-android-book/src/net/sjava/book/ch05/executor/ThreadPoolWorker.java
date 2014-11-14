package net.sjava.book.ch05.executor;

import android.util.Log;

public class ThreadPoolWorker implements Runnable {
	static final String CNAME = ThreadPoolWorker.class.getSimpleName();
	private String name;
	
	public ThreadPoolWorker(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		Log.d(CNAME, Thread.currentThread().getName() + " 시작");
		
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			Log.e(CNAME, "에러", e);
		}
		
		Log.d(CNAME, Thread.currentThread().getName() + " 종료");
	}

	@Override
	public String toString() {
		return name;
	}
}
