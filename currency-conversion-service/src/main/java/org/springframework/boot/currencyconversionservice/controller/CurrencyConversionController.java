package org.springframework.boot.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.currencyconversionservice.dto.CurrencyConversionBean;
import org.springframework.boot.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@GetMapping("currency-converter/from/{from}/to/{to}/quantity/{quantity}") 
	public CurrencyConversionBean convertCurrency(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {
		Map<String, String> uriVariable = new HashMap<String, String>();
		uriVariable.put("from", from);
		uriVariable.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,uriVariable);
		CurrencyConversionBean response = responseEntity.getBody();
		logger.info("Conversion Controller->{}", response);
		return new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,
				response.getConversionMultiple().multiply(quantity),response.getPort());
	}

	
	@GetMapping("currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}") 
	public CurrencyConversionBean convertCurrencyFegin(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {		
		//Feign is a rest client, It's very easy to call rest service
		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
		return new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,
				response.getConversionMultiple().multiply(quantity),response.getPort());
	}
}
