package com.test.trading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.test.trading.util.TradeStore;

public class MainClass {

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void main(String[] args) {
		TradeStore tradeStore=new TradeStore();
		final Runnable expireTradeJob = new Runnable() {
			public void run() {
				tradeStore.expireTrade();
			}
		};
		
		scheduler.schedule(expireTradeJob, 24, TimeUnit.HOURS);
	}

	

}
