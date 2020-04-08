package org.springframework.boot.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.currencyconversionservice.dto.CurrencyConversion;
import org.springframework.boot.currencyconversionservice.dto.CurrencyConversionBean;
import org.springframework.boot.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
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
	public ResponseEntity<CurrencyConversionBean> convertCurrencyFegin(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {		
		//Feign is a rest client, It's very easy to call rest service
		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
		CurrencyConversionBean bean = new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,
				response.getConversionMultiple().multiply(quantity),response.getPort());
		return ResponseEntity.status(HttpStatus.OK).body(bean);
	}
	
	@GetMapping("currency-converter-exchange-values") 
	public CurrencyConversion exchangeValues() {
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange", CurrencyConversion.class);
		CurrencyConversion response = responseEntity.getBody();
		logger.info("Conversion Controller->{}", response);
		return response;
	}
	
	@GetMapping("/currency-converter-exchange-values-feign") 
	public ResponseEntity<CurrencyConversion> exchangeValuesFegin() {		
		//Feign is a rest client, It's very easy to call rest service
		CurrencyConversion response = proxy.retrieveExchangeValues(); 
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/currency-converter-exchange-value/id/{id}") 
	public ResponseEntity<CurrencyConversionBean> exchangeValuesFegin(@PathVariable Long id) {		
		//Feign is a rest client, It's very easy to call rest service
		CurrencyConversionBean response = proxy.retrieveExchangeValue(id); 
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/currency-converter-exchange-value/id/{id}") 
	public ResponseEntity<Void> deleteExchangeValue(@PathVariable Long id){
		proxy.deleteExchangeValue(id);
		return ResponseEntity.noContent().build();		
	}
	
	@PostMapping("/currency-converter-exchange-value")
	public ResponseEntity<Void> createExchangeValue(@RequestBody CurrencyConversionBean value) {
		proxy.newExchangeValue(value);
		Map<String, String> map = new HashMap<String, String>();
		map.put("from", value.getFrom());
		map.put("to", value.getTo());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/from/{from}")
			.path("/to/{to}")
			.buildAndExpand(map).toUri();			
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/currency-converter-exchange-value")
	public ResponseEntity<Void> updateExchangeValue(@RequestBody CurrencyConversionBean value) {
		proxy.newExchangeValue(value);	
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
