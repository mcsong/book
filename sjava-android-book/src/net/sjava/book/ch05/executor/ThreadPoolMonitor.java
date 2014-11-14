package net.sjava.book.ch05.executor;

import java.util.concurrent.ThreadPoolExecutor;

import android.util.Log;

public class ThreadPoolMonitor implements Runnable {
	static final String CNAME = ThreadPoolMonitor.class.getSimpleName();
	
	private boolean isRunning = true;
	private ThreadPoolExecutor executor;

	public ThreadPoolMonitor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public void shutdown() {
		this.isRunning = false;
	}

	@Override
	public void run() {
		while (isRunning) {
			Log.d(CNAME, String.format("[%d/%d] 활성 스레드 수 : %d, "
							+ "완료된 태스크 : %d, "
							+ "태스크 : %d, "
							+ "ThreadPoolExecutor 종료여부 : %s, "
							+ "ThreadPoolExecutor와 태스크의 종료여부 : %s",
							executor.getPoolSize(), 
							executor.getCorePoolSize(),
							executor.getActiveCount(),
							executor.getCompletedTaskCount(),
							executor.getTaskCount(), executor.isShutdown(),
							executor.isTerminated()));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e(CNAME, "에러", e);
			}
		}
	}
}
