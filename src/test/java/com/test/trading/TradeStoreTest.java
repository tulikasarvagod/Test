package com.test.trading;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.test.trading.model.Trade;
import com.test.trading.model.TradeId;
import com.test.trading.util.TradeStore;

public class TradeStoreTest {
	
	private TradeStore tradeStore;
	private ExecutorService e;
	private volatile int skipCount=2;
	private volatile int i=1;
	
	@BeforeEach
	public void init() {
		e = Executors.newFixedThreadPool(25);
		tradeStore=new TradeStore();	
		
		 for(; i < 5000; i++){
			 e.submit(new Runnable(){
	               public void run(){ 	            	   
	            	Trade trade=   createTrade("T"+skipCount, skipCount, "CP-"+i, "B"+i, getDate(), Character.valueOf('N'));
	            	try {
						tradeStore.addTrade(trade);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						skipCount=skipCount+2;
					}
	            	   
	               } 
	           });
			
		 }
	}
	
	@Test
	public void testAddTrade() throws Exception {
		Trade trade0=   createTrade("T"+2, 4, "CP-"+i, "B"+i, LocalDate.of(2022, 6, 22), Character.valueOf('N'));
		Trade trade1=   createTrade("T"+2, 2, "CP-"+i, "B"+i,LocalDate.of(2021, 6, 22), Character.valueOf('N'));
		Trade trade2=   createTrade("T"+2, 1, "CP-"+i, "B"+i, LocalDate.of(2023, 6, 22), Character.valueOf('N'));		
		tradeStore.addTrade(trade1);
		tradeStore.addTrade(trade0);
		String message="The lower version of trade " + trade2.getTradeId().getId() + " cannot be accepted.";
		Exception exception = assertThrows(Exception.class, () -> {
		      tradeStore.addTrade(trade2);
		    });
		Assertions.assertEquals(exception.getMessage(),message);
	}
	
	@Test
	public void testAddExpiredTrade() throws Exception {
		Trade trade0=   createTrade("T"+3, 4, "CP-"+i, "B"+i, LocalDate.of(2019, 6, 22), Character.valueOf('N'));		
		tradeStore.addTrade(trade0);
		
		Assertions.assertFalse(trade0.isValid());
	}
	
	
	private Trade createTrade(String tradeId, int version,String counterPartyId,String bookId,LocalDate maturityDate,Character expired) {
		Trade trade=new Trade();
		trade.setTradeId(new TradeId(tradeId,version));
		trade.setCounterPartyId(counterPartyId);
		trade.setBookId(bookId);
		Calendar date=Calendar.getInstance();
		date.set(maturityDate.getYear(), maturityDate.getMonthValue(), maturityDate.getDayOfMonth());
		trade.setMaturityDate(date.getTime());
		trade.setCreatedDate(new Date());
		trade.setExpired(expired);
		
		return trade;
	}
	private LocalDate getDate() {
	    int hundredYears = 100 * 365;
	    return LocalDate.ofEpochDay(ThreadLocalRandom
	      .current().nextInt(2022, hundredYears));
	}
	
}
