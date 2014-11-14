package net.sjava.book.ch05;

import android.os.Process;
import android.util.Log;

public class ThreadTest extends Thread {
	static final String CNAME = ThreadTest.class.getSimpleName();
	
	public void run() {
		int tid = Process.myTid();
		
		Log.d(CNAME, getName() + " 우선순위 [" + getPriority() + "] 로 실행");
		Log.d(CNAME, getName() + " 우선순위 [" + Process.getThreadPriority(tid) + "] 로 실행");
	}
}