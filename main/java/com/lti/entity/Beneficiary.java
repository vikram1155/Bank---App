package com.lti.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="proj_bene")
public class Beneficiary {
	@Id
	@SequenceGenerator(name="bene_seq",initialValue=500,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="bene_seq")
	int beneficiaryId;
	String beneficiaryName;
	int toAccountNo;


	@ManyToOne
	@JoinColumn(name="accountNo")
	Account account;
	
	@ManyToOne
	@JoinColumn(name="customerId")
	Customer customer;
	

	public Beneficiary() {
		
	}

	public Beneficiary(int beneficiaryId, String beneficiaryName, int toAccountNo) {
		super();
		this.beneficiaryId = beneficiaryId;
		this.beneficiaryName = beneficiaryName;
		this.toAccountNo = toAccountNo;
	}

	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public int getToAccountNo() {
		return toAccountNo;
	}

	public void setToAccountNo(int toAccountNo) {
		this.toAccountNo = toAccountNo;
	}

//	@JsonIgnore
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@JsonIgnore
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	
	
}
