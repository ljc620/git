package test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestThread {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (int i = 1; i <= 1000; i++) {
			SaleTicketTestByYL s = new SaleTicketTestByYL(String.valueOf(i));
			Future f = pool.submit(s);
		}
		pool.shutdown();
	}
}
