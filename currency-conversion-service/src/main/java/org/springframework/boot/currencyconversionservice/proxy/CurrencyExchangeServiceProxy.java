package org.springframework.boot.currencyconversionservice.proxy;

import org.springframework.boot.currencyconversionservice.dto.CurrencyConversion;
import org.springframework.boot.currencyconversionservice.dto.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name="currency-exchange-service",url="localhost:8000")
//@FeignClient(name="currency-exchange-service")
@FeignClient(name="netflix-zuul-api-getway-server")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
	
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from,@PathVariable("to") String to);
	
	@GetMapping("/currency-exchange-service/currency-exchange")
	public CurrencyConversion retrieveExchangeValues();
	
	@GetMapping("/currency-exchange-service/currency-exchange/id/{id}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("id") Long id);
	
	@DeleteMapping("/currency-exchange-service/currency-exchange/id/{id}")
	public void deleteExchangeValue(@PathVariable("id") Long id);
	
	@PostMapping("/currency-exchange-service/currency-exchange")
	public ResponseEntity<Void> newExchangeValue(@RequestBody CurrencyConversionBean value);
	
	@PutMapping("/currency-exchange-service/currency-exchange")
	public ResponseEntity<Void> updateExchangeValue(@RequestBody CurrencyConversionBean value);
}
