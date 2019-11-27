package org.learn.dao;

import org.learn.dto.Policy;
import org.springframework.data.repository.CrudRepository;

public interface PolicyRepository extends CrudRepository<Policy,String>{
	
	//@Query("Select a from Rule ")
	//Rule fetchRule(@Param("policyId") String policyId, @Param("ruleId") String ruleId); 
	
	Policy findBypolicyId(String policyId);

}
