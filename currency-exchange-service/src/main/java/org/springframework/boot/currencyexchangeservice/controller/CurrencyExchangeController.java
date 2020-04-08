package org.springframework.boot.currencyexchangeservice.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.currencyexchangeservice.dao.ExchangeValueRepository;
import org.springframework.boot.currencyexchangeservice.dto.ExchangeValue;
import org.springframework.boot.currencyexchangeservice.dto.ExchangeValues;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository exchangeValueRepository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ResponseEntity<ExchangeValue> retrieveExchangeValue(@PathVariable String from,@PathVariable String to) {
		int port = Integer.parseInt(environment.getProperty("local.server.port"));
		ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to); 
		if(exchangeValue==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		exchangeValue.setPort(port);
		logger.info("Exchange Controller->{}", exchangeValue);
		return ResponseEntity.status(HttpStatus.OK).body(exchangeValue);		
	}
	
	@GetMapping("/currency-exchange")
	public ResponseEntity<ExchangeValues> retrieveExchangeValue() {
		List<ExchangeValue> exchangeValue =  exchangeValueRepository.findAll();
		ExchangeValues values = new ExchangeValues();
		values.setExchangeValues(exchangeValue);		
		return ResponseEntity.status(HttpStatus.OK).body(values);
	}
	
	@GetMapping("/currency-exchange/id/{id}")
	public ResponseEntity<ExchangeValue> retrieveExchangeValueById(@PathVariable Long id) {
		int port = Integer.parseInt(environment.getProperty("local.server.port"));
		ExchangeValue exchangeValue =  exchangeValueRepository.findById(id).get();
		if(exchangeValue==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		exchangeValue.setPort(port);
		logger.info("Exchange Controller->{}", exchangeValue);
		return ResponseEntity.status(HttpStatus.OK).body(exchangeValue);
	}
	
	@PostMapping("/currency-exchange")
	public ResponseEntity<Void> createExchangeValue(@RequestBody ExchangeValue value) {		
		exchangeValueRepository.save(value);
		Map<String, String> map = new HashMap<String, String>();
		map.put("from", value.getFrom());
		map.put("to", value.getTo());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/from/{from}")
			.path("/to/{to}")
			.buildAndExpand(map).toUri();			
		return ResponseEntity.created(uri).build();		
	}
	
	@PutMapping("/currency-exchange")
	public ResponseEntity<Void> updateExchangeValue(@RequestBody ExchangeValue value) {		
		ExchangeValue updatedValue = exchangeValueRepository.save(value);
		Map<String, String> map = new HashMap<String, String>();
		map.put("from", value.getFrom());
		map.put("to", value.getTo());					
		return ResponseEntity.status(HttpStatus.OK).build();		
	}
	
	@DeleteMapping("/currency-exchange/id/{id}")
	public ResponseEntity<Void> deleteExchangeValue(@PathVariable Long id) {		
		exchangeValueRepository.deleteById(id);							
		return ResponseEntity.noContent().build();		
	}
	
	
}
