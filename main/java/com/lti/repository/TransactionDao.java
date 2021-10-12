package com.lti.repository;

import java.util.List;

import com.lti.dto.StatementDto;
import com.lti.entity.Account;
import com.lti.entity.Beneficiary;
import com.lti.entity.Register;
import com.lti.entity.Transaction;

public interface TransactionDao {

	
//	addTransaction(int fromAct, int toAcc);
//	viewTransactionByAccoutNo(int accountNo);
//	viewAccountDetails(int accountNo);

	Account addAccountWithMultipleTransactions(Account account);

	//1st method
	Transaction addATransactionToAnAccount(Transaction transaction);
	
	//2nd method
	List<Transaction> fetchAllTransactionsOfAnAccount(int accountNo);

	//3rd
	Account fetchAccountDetailsByTransactionId(int transactionId);
	
	//4th
	Beneficiary addBeneficiaryToAnAccount(Beneficiary beneficiary);
	
	//5th
	List<Beneficiary> fetchAllBeneficiaryOfAnAccount(int accountNo);

    //6th
    List<Account> updateAccountBalanceOnTransaction(Beneficiary beneficiary,int amount);
    
    //7th
    Beneficiary findBeneficiaryById(int beneficiaryId);
    
    
    public Register addNetBankingAccount(Register register);
    
    //check if account is already registered for net banking
    public long checkAccountForNetBanking(int accountNo);
    
    public long checkBeneficiaryforAccount(int fromAccountNo, int toAccountNo);
    
    public List<Beneficiary> fetchAllBeneficiaryOfCustomer(int custId);
    
    public List<Transaction> fetchTransactionByDate(StatementDto statementDto);
    
    public Register getRegisterByAccountNo(int accountNo);
    
    

}

