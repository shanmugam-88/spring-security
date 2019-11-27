package org.learn.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
@ApiModel(value="Class that will hold the Details of the policy")
@Entity
public class Policy {
	@ApiParam(value="If of the Policy",required=true)
	@Id
	@Column(name = "policy_id",length=20)
	private String policyId;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="policy",cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Rule> rules;

	
	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	
	
	}
