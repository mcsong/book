package net.sjava.book.ch08;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LIFOLinkedBlockingQueue<E> extends LinkedBlockingDeque<E> {
	private static final long serialVersionUID = 8571343048144992043L;

	public LIFOLinkedBlockingQueue(int capacity) {
		super(capacity);
	}
	
	@Override
	public boolean add(E e) {
		addFirst(e);
		return true;
	}

	@Override
	public boolean offer(E e) {
		return offerFirst(e);
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return offerFirst(e, timeout, unit);
	}

	@Override
	public void put(E e) throws InterruptedException {
		putFirst(e);
	}
	
	@Override
	public E take() throws InterruptedException {
		return takeLast();
	}
}