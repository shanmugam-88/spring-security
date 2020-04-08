package org.springframework.boot.currencyconversionservice.dto;

import java.util.List;

public class CurrencyConversion {
	
	private List<CurrencyConversionBean> exchangeValues;

	public List<CurrencyConversionBean> getExchangeValues() {
		return exchangeValues;
	}

	public void setExchangeValues(List<CurrencyConversionBean> exchangeValues) {
		this.exchangeValues = exchangeValues;
	}

}
