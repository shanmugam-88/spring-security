package org.learn.service;

import java.util.ArrayList;
import java.util.List;

import org.learn.dao.PolicyRepository;
import org.learn.dao.RuleRepository;
import org.learn.dto.Policy;
import org.learn.dto.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

	@Autowired
	private PolicyRepository policyRepository;

	@Autowired
	private RuleRepository ruleRepository;
	
	private static Logger _logger = LoggerFactory. getLogger("Service");

	public List<Policy> getAllPolicies() {
		List<Policy> policies = new ArrayList<Policy>();
		policyRepository.findAll().forEach(policies::add);
		_logger.info("Service is called and returning the size of "+ policies.size());
		return policies;
	}

	public Policy getPolicy(String policyId) {
		return policyRepository.findBypolicyId(policyId);
	}

	public void addPolicy(Policy policy) {
		policy.getRules().forEach(rule -> { rule.setRuleId(policy.getPolicyId()+"_"+rule.getRuleId()); });
		policyRepository.save(policy);
	}

	public void updatePolicy(Policy policy) {
		policyRepository.save(policy);
	}
	
	public void deletePolicy(String policyId) {
		policyRepository.delete(this.getPolicy(policyId));
	}

	public List<Rule> getRules(String policyId) {
		return ruleRepository.fetchAllRule(policyId);
	}

	public Rule getRuleByPolicyAndRuleId(String policyId, String ruleId) {
		return ruleRepository.fetchRule(policyId, ruleId);
	}

	/*
	 * public Rule getRuleByPolicyAndRuleId(String policyId,String ruleId) { return
	 * policyRepository.findById(policyId).get().getRules().stream().filter(rule->
	 * ruleId.equals(rule.getRuleId())).findAny().orElse(null); }
	 */
}
