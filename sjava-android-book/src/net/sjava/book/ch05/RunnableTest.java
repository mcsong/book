package net.sjava.book.ch05;

import android.os.Process;
import android.util.Log;

public class RunnableTest implements Runnable {
	static final String CNAME = RunnableTest.class.getSimpleName();

	@Override
	public void run() {
		int tid = Process.myTid();

		Log.d(CNAME, Thread.currentThread().getName() + " 우선순위 ["
				+ Thread.currentThread().getPriority() + "] 로 실행");
		Log.d(CNAME,
				Thread.currentThread().getName() + " 우선순위 ["
						+ Process.getThreadPriority(tid) + "] 로 실행");
	}
}
