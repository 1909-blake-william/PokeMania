package com.revature.util;

import com.revature.TradeStatus;

public class Parser {

	public static TradeStatus parseTradeStatus(String status) {
		
		try {
			
			return TradeStatus.valueOf(status.toUpperCase());
			
		} catch(Exception e) {
			
			return TradeStatus.INVALID;
			
		}
		
	}
	
}
