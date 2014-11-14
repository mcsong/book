package net.sjava.book.ch05.future;

import java.util.concurrent.Callable;

public class FutureTaskWorker implements Callable<Integer> {
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
