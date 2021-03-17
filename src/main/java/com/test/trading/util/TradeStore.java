package com.test.trading.util;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.test.trading.model.Trade;

public class TradeStore {
	private  ConcurrentHashMap<String, Trade> storeMap;

	public void addTrade(Trade trade) throws Exception {
		if (storeMap == null) {
			synchronized (TradeStore.class) {
				if (storeMap == null) {
					storeMap = new ConcurrentHashMap<String, Trade>();
				}
			}			
		}		
		
		if (trade.isValid()) {
			if (!storeMap.containsKey(trade.toString())) {
				List<String> tradeIds = storeMap.keySet().stream()
						.filter(tradeId -> tradeId.startsWith(trade.getTradeId().getId()+"-"))
						.sorted(Comparator.reverseOrder()).collect(Collectors.toList());

				if (!tradeIds.isEmpty() && trade.toString().compareTo(tradeIds.get(0)) < 0) {
					// For Trade with less version
					throw new Exception(
							"The lower version of trade " + trade.getTradeId().getId() + " cannot be accepted.");
				} else {
					storeMap.put(trade.toString(), trade);
				}
			} else {
				storeMap.put(trade.toString(), trade);
			}
		}
	}

	public void expireTrade() {
		if (!storeMap.isEmpty()) {
			storeMap.values().stream().filter(e -> !e.isValid()).forEach(e -> e.setExpired(Character.valueOf('Y')));
		}
	}
}
