package com.lti.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.repository.AccountDao;
import com.lti.repository.TransactionDao;
import com.lti.dto.StatementDto;
import com.lti.entity.Account;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Register;
import com.lti.entity.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	AccountDao accDao;
	
	@Override
	public Account addAccountWithMultipleTransactions(Account account) {
		
		return transactionDao.addAccountWithMultipleTransactions(account);
	}

	@Override
	public Transaction addATransactionToAnAccount(Transaction transaction) {

		return transactionDao.addATransactionToAnAccount(transaction);
	}
	
	@Override
	public List<Transaction> fetchAllTransactionsOfAnAccount(int accountNo) {
		
		return transactionDao.fetchAllTransactionsOfAnAccount(accountNo);
	}

	@Override
	public Account fetchAccountDetailsByTransactionId(int transactionId) {
		
		return transactionDao.fetchAccountDetailsByTransactionId(transactionId);
	}

	@Override
	public List<Account> updateAccountBalanceOnTransaction(Beneficiary beneficiary,int amount) {
		
		return transactionDao.updateAccountBalanceOnTransaction(beneficiary,amount);
	}

	@Override
	public Beneficiary addBeneficiaryToAnAccount(Beneficiary beneficiary) {
		
		return transactionDao.addBeneficiaryToAnAccount(beneficiary);
	}

	@Override
	public List<Beneficiary> fetchAllBeneficiaryOfAnAccount(int accountNo) {
		
		return transactionDao.fetchAllBeneficiaryOfAnAccount(accountNo);
	}

	@Override
	public Beneficiary findBeneficiaryById(int beneficiaryId) {
		return transactionDao.findBeneficiaryById(beneficiaryId);
	}

	@Override
	public Register addNetBankingAccount(Register register, Customer c) {
		int custId = c.getCustomerId();
//		String text="Successfully registered. Your id is "+id+ ".  You rae successfully registered for Net banking. Now you can transfer easily ";
//		String subject="Net Banking Registration Confirmation";
//		emailService.sendEmailForNewRegistration(c.getEmail(), text, subject);
		return transactionDao.addNetBankingAccount(register);
		
	}

	@Override
	public long checkAccountForNetBanking(int accountNo) {
		// TODO Auto-generated method stub
		return transactionDao.checkAccountForNetBanking(accountNo);
	}

	@Override
	public long checkBeneficiaryforAccount(int fromAccountNo, int toAccountNo) {
		// TODO Auto-generated method stub
		return transactionDao.checkBeneficiaryforAccount(fromAccountNo, toAccountNo);
	}

	@Override
	public List<Beneficiary> fetchAllBeneficiaryOfCustomer(int customerId) {
		// TODO Auto-generated method stub
		return transactionDao.fetchAllBeneficiaryOfCustomer(customerId);
	}

	@Override
	public List<Transaction> fetchTransactionByDate(StatementDto statementDto) {
		// TODO Auto-generated method stub
		return transactionDao.fetchTransactionByDate(statementDto);
	}

	@Override
	public Register getRegisterByAccountNo(int accountNo) {
		// TODO Auto-generated method stub
		return transactionDao.getRegisterByAccountNo(accountNo);
	}

	


	
}
