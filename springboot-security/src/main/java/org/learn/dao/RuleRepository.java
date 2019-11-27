package org.learn.dao;

import java.util.List;

import org.learn.dto.Rule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RuleRepository extends CrudRepository<Rule,String>{
	
	/*
	 * Rule findByruleIdAndPolicyId(String ruleId,String policyId); List<Rule>
	 * findBypolicyId(String policyId);
	 */
	
	  @Query("select r from Rule as r inner join Policy as p on p.id = r.policy where p.policyId=:policyId ") 
	  List<Rule> fetchAllRule(@Param("policyId") String policyId);
	  
	  @Query("select r from Rule as r inner join Policy as p on p.id = r.policy where p.policyId=:policyId and r.ruleId=:ruleId") 
	  Rule fetchRule(@Param("policyId") String policyId,@Param("ruleId") String ruleId);

}
