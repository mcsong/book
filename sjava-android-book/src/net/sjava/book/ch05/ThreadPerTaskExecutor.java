package net.sjava.book.ch05;
import java.util.concurrent.Executor;

class ThreadPerTaskExecutor implements Executor {
	public void execute(Runnable r) {
		new Thread(r).start();
	}
}