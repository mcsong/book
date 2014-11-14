package net.sjava.book.ch05.callable;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.out;

public class CallableTest {
	public static void main(String[] args) {
		ArrayList<String> arrays = new ArrayList<String>();
		arrays.add("111");
		arrays.add("222");
		arrays.add("333");
		arrays.add("444");
		arrays.add("555");

		ExecutorService executor = Executors.newFixedThreadPool(2);
		ArrayList<Future<String>> rvalues = new ArrayList<Future<String>>();

		try {
			for (String value : arrays)
				rvalues.add(executor.submit(new CallableWorker(value)));

			for (Future<String> result : rvalues)
				out.println(result.get());

		} catch (Exception e) {
			e.printStackTrace();
		}

		executor.shutdown();
	}
}
