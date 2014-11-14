package net.sjava.book.ch08;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPoolExecutor {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
    private static final RejectedHandler rejectHandler = new RejectedHandler();
    
    public static final ThreadPoolExecutor POOL_EXECUTOR;    
    static {
    	POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TIME_UNIT, sPoolWorkQueue, sThreadFactory, rejectHandler);
    }
    
    private CustomThreadPoolExecutor() {}
    
	static class RejectedHandler implements RejectedExecutionHandler {
		@Override
		public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {			
			try {
				executor.getQueue().take();
				executor.getQueue().put(task);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
