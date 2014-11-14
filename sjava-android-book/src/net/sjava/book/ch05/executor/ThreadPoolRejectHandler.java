package net.sjava.book.ch05.executor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import android.util.Log;

public class ThreadPoolRejectHandler implements RejectedExecutionHandler {
	static final String CNAME = ThreadPoolRejectHandler.class.getSimpleName();
	
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		Log.d(CNAME, r.toString() + " °ÅÀýµÊ");
	}
}
