package com.lti.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name= "proj_verification")
public class Verification {
	
	@Id
	@GeneratedValue
	int verificationId;
	
	ApprovalStatus status = ApprovalStatus.APPLIED;
	
	@SequenceGenerator(name="pass_seq", initialValue=50000, allocationSize=10)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pass_seq")
	String password;
	
//	@OneToOne
//	@JoinColumn(name="accountNo")
//	Account account;
	
	@OneToOne
	@JoinColumn(name="customerId")
	Customer customer;
	
	public Verification() {
		
	}

	
	public int getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(int verificationId) {
		this.verificationId = verificationId;
	}

	public ApprovalStatus getStatus() {
		return status;
	}

	public void setStatus(ApprovalStatus status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Verification [verificationId=" + verificationId + ", status=" + status + ", password=" + password + "]";
	}
	
	
	
	
	
}
