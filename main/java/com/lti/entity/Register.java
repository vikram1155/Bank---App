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
@Table(name="proj_reg")
public class Register {
	
	@Id
	@SequenceGenerator(name="reg_seq",initialValue=5000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="reg_seq")
	int registerId;
	
	
	String transactionPassword;
	
    @OneToOne
	@JoinColumn(name="accountNo")
	Account account;
    
    
	public Register() {
		
	}

	public Register(int registerId, String transactionPassword) {
		super();
		this.registerId = registerId;
		this.transactionPassword = transactionPassword;
	}

	public int getRegisterId() {
		return registerId;
	}

	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	

}
