package net.sjava.book.ch05.callable;

import java.util.concurrent.Callable;

public class CallableWorker implements Callable<String> {
	private String name;
	public CallableWorker(String name) {
		this.name = name;
	}
	
	@Override
	public String call() throws Exception {
		Thread.sleep(1 * 1000);
		return name + ":" + Thread.currentThread().getName();
	}
}
