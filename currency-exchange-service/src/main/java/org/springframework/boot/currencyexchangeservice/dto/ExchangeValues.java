package org.springframework.boot.currencyexchangeservice.dto;

import java.util.List;

public class ExchangeValues {	
	
	private List<ExchangeValue> exchangeValues;

	public List<ExchangeValue> getExchangeValues() {
		return exchangeValues;
	}

	public void setExchangeValues(List<ExchangeValue> exchangeValues) {
		this.exchangeValues = exchangeValues;
	}
	
	

}
