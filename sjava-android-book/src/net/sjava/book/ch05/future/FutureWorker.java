package net.sjava.book.ch05.future;

import java.util.concurrent.Callable;

public class FutureWorker implements Callable<String> {
	private int count;

	public FutureWorker(int count) {
		this.count = count;
	}

	public String call() {
		try {
			Thread.sleep(2000);
		} catch(Exception e) {}
		
		return Thread.currentThread().getName() +":" + count;
	}
}
