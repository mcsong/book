package net.sjava.book.ch07;

import android.os.Process;

public class ForegroundWorker extends Thread {

	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND);
		long start = System.currentTimeMillis();
		
		int value = 0;
		try {
			for(int i=0; i < 100; i++) {
				value += i;
				//Thread.sleep(100);
			}
		} catch(Exception e) {}
		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("ForegroundWorker 처리시간 : " + elapsedTime +" : " + value);
	}
}
