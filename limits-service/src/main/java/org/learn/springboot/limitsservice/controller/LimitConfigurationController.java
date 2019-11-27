package org.learn.springboot.limitsservice.controller;

import org.learn.springboot.limitsservice.bean.LimitConfiguration;
import org.learn.springboot.limitsservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitConfigurationController {
	
	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	public LimitConfiguration retrievelLimitsFromConfiguations() {
		return new LimitConfiguration(configuration.getMinimum(), configuration.getMaximum());
		
	}
}
