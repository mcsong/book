package net.sjava.book.ch05.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.System.out;

public class FutureTaskExample {
	
	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		FutureTask<Integer> f1 = new FutureTask<Integer>(new FutureTaskWorker(true, 1000));
		FutureTask<Integer> f2 = new FutureTask<Integer>(new FutureTaskWorker(false, 1000));
		FutureTask<Integer> f3 = new FutureTask<Integer>(new FutureTaskWorker(false, 1000));
		
		executor.submit(f1);
		executor.submit(f2);
		executor.submit(f3);
		
		executor.shutdown();
		
		out.println("f1 isDone : "+ f1.isDone());
		while(!f1.isDone()) {
			Thread.sleep(100);
		}
		
		out.println("f1 isDone : "+ f1.isDone()+" return value : " + f1.get() );
		out.println("f2 isDone : "+ f2.isDone()+" return value : " + f2.get() );
		
		try{
			f3.get(1, TimeUnit.SECONDS);
		} catch(TimeoutException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}

	static class FutureTaskWorker implements Callable<Integer> {
		private boolean increment;
		private int count;

		public FutureTaskWorker(boolean increment, int count) {
			this.increment = increment;
			this.count = count;
		}

		public Integer call() {
			try {
				Thread.sleep(2000);
			} catch(Exception e) {}
			
			if(increment)
				return count;
			
			return 0;
		}
	}
}
