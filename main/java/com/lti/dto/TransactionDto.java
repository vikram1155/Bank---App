package com.lti.dto;

import java.time.LocalDate;

import com.lti.entity.Account;
import com.lti.entity.TransferType;

public class TransactionDto {

	
	/*  beneficiaryId: number;
    beneficiaryName: string;
	toAccountNo: number;
    customerId: any;
    fromAccountNo: number;
    amount: number;
    tpassword: number;
    transferType: string;
    description: string;
    today:number=Date.now();*/
	
	int beneficiaryId;
	String beneficiaryName;
	int toAccountNo;
	String tpassword;
	String description;
	String transactionDate;
	int amount;
	TransferType transactionType;
	int fromAccountNo;
	

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

	public String getTpassword() {
		return tpassword;
	}
	public void setTpassword(String tpassword) {
		this.tpassword = tpassword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public TransferType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransferType transactionType) {
		this.transactionType = transactionType;
	}
	public int getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}



	
	
	
	
}
