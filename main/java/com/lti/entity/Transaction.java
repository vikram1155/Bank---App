package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="proj_tran")
public class Transaction {
	@Id
	@SequenceGenerator(name="tran_seq",initialValue=10500,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="tran_seq")
	int transactionId;
	
	int toAccountNumber;
	int amount;
	LocalDate transactionDate;
	String description;
	TransferType transferType;
	

	@ManyToOne
	@JoinColumn(name="accountNo")
	Account account;
	
	

	public Transaction() {
		
	}

	public Transaction(int transactionId, int toAccountNumber, int amount, LocalDate transactionDate,
			String description) {
		super();
		this.transactionId = transactionId;
		this.toAccountNumber = toAccountNumber;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.description = description;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(int toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public TransferType getTransferType() {
		return transferType;
	}

	public void setTransferType(TransferType transferType) {
		this.transferType = transferType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}
