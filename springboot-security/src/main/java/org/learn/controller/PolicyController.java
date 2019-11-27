package org.learn.controller;

import java.util.List;

import org.learn.dto.Policy;
import org.learn.dto.Rule;
import org.learn.service.PolicyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/policyhandler")
public class PolicyController {
	
	private static Logger _logger = LoggerFactory.getLogger("Controller");

	@Autowired
	private PolicyService policyService;

	@RequestMapping(method = RequestMethod.GET, value = "/policy")
	@ApiOperation(value = "Get All policies from the System", notes = "API whcih we can use to get all policies from the source system", response = Policy.class)
	public ResponseEntity<List<Policy>> getAllPolicies() {
		_logger.info("Get all policy has been called");
		return new ResponseEntity<List<Policy>>(policyService.getAllPolicies(),HttpStatus.OK);//policyService.getAllPolicies();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/policy/{id}")
	@ApiOperation(value = "Get policy by Policy id from the System", notes = "API whcich we can use to get a policy from the source system by policy id", response = Policy.class)
	public Policy getPolicy(
			@ApiParam(value = "PolicyId for the Policy which you need to retrieve", required = true) @PathVariable String id) {
		return policyService.getPolicy(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/policy")
	public void createPolicy(@RequestBody Policy policy) {
		policyService.addPolicy(policy);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/policy")
	public void updatePolicy(@RequestBody Policy policy) {
		policyService.addPolicy(policy);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/policy/{id}")
	@ApiOperation(value = "Delete policy by Policy id from the System", notes = "API whcich we can use to delete a policy from the source system by policy id", response = Policy.class)
	public void deletePolicy(
			@ApiParam(value = "PolicyId for the Policy which you need to delete", required = true) @PathVariable String id) {
		policyService.deletePolicy(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/policy/{id}/rules")
	public List<Rule> getRules(@PathVariable String id) {
		return policyService.getRules(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/policy/{policyId}/rule/{ruleId}")
	public Rule getRule(@PathVariable String policyId, @PathVariable String ruleId) {
		return policyService.getRuleByPolicyAndRuleId(policyId, ruleId);
	}

}
