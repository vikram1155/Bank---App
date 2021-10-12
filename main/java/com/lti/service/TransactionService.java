package com.lti.service;

import java.util.List;

import com.lti.dto.StatementDto;
import com.lti.entity.Account;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Register;
import com.lti.entity.Transaction;

public interface TransactionService {
	Account addAccountWithMultipleTransactions(Account account);

	Transaction addATransactionToAnAccount(Transaction transaction);
	
	List<Transaction> fetchAllTransactionsOfAnAccount(int accountNo);

	Account fetchAccountDetailsByTransactionId(int transactionId);
	
    List<Account> updateAccountBalanceOnTransaction(Beneficiary beneficiary,int amount);
    
	Beneficiary addBeneficiaryToAnAccount(Beneficiary beneficiary);
	
	
	List<Beneficiary> fetchAllBeneficiaryOfAnAccount(int accountNo);
	
	 Beneficiary findBeneficiaryById(int beneficiaryId);
	  
	 public Register  addNetBankingAccount(Register register, Customer c);
	  
	//check if account is already registered for net banking
	    public long checkAccountForNetBanking(int accountNo);

	    public long checkBeneficiaryforAccount(int fromAccountNo, int toAccountNo);
	    
	    public List<Beneficiary> fetchAllBeneficiaryOfCustomer(int customerId);
	    
	    public List<Transaction> fetchTransactionByDate(StatementDto statementDto);
	    
	    public Register getRegisterByAccountNo(int accountNo);
	    
}
