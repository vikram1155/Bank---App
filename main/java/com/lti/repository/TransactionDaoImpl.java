package com.lti.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.dto.StatementDto;
import com.lti.entity.Account;
import com.lti.entity.ApprovalStatus;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Register;
import com.lti.entity.Transaction;

@Repository
public class TransactionDaoImpl implements TransactionDao {
	
	@PersistenceContext
	EntityManager em;
	
	CustomerDao customerDao;
	
	@Autowired
	AccountDao accDao;
	
	@Override
	@Transactional
	public Transaction addATransactionToAnAccount(Transaction transaction) {
		Transaction t = em.merge(transaction);
		return t;
	}
	
	

	@Override
	public Account fetchAccountDetailsByTransactionId(int transactionId) {
		return em.find(Transaction.class, transactionId).getAccount();
	}

	@Override
	@Transactional
	public Beneficiary addBeneficiaryToAnAccount(Beneficiary beneficiary) {
		Beneficiary bene = em.merge(beneficiary);
		return bene;
	}
	
	@Override
	public List<Beneficiary> fetchAllBeneficiaryOfAnAccount(int accountNo) {
		String jpql = "select b from Beneficiary b where accountNo=:accId";
		Query query = em.createQuery(jpql);
		query.setParameter("accId", accountNo);
		List<Beneficiary> beneficiary = query.getResultList();
		return beneficiary;
	}
	@Override
	public Beneficiary findBeneficiaryById(int beneficiaryId) {
		return em.find(Beneficiary.class,beneficiaryId);
	}
	
	@Override
	@Transactional
	public Register addNetBankingAccount(Register register) {
		Register r = em.merge(register);
		return r;
	}
	
	@Override
	public long checkAccountForNetBanking(int accountNo) {
		String jpql = "select count(r.account) from Register r where r.account = (select a.accountNo from Account a where a.accountNo=: accNo) ";
		Query query = em.createQuery(jpql);
		query.setParameter("accNo", accountNo);
		long count = (long) query.getSingleResult();
		return count;
	}
	
	@Override
	public long checkBeneficiaryforAccount(int fromAccountNo, int toAccountNo) {
		String jpql = "select count(b) from Beneficiary b where b.toAccountNo =: toAcc AND (b.account= (select a.accountNo from Account a where a.accountNo=: fromAccNo)) ";
		Query query = em.createQuery(jpql);
		query.setParameter("toAcc", toAccountNo);
		query.setParameter("fromAccNo", fromAccountNo);
		long count = (long) query.getSingleResult();
		return count;		
	}
	
	@Override
	public List<Beneficiary> fetchAllBeneficiaryOfCustomer(int custId) {
		String jpql = "select b from Beneficiary b where customer= (select c from Customer c where c.customerId =: cId)";
		Query query = em.createQuery(jpql);
		query.setParameter("cId", custId);
		List<Beneficiary> beneficiaries = query.getResultList();
		return beneficiaries;
	}
	
	@Override
	public List<Transaction> fetchAllTransactionsOfAnAccount(int accountNo) {
		String jpql = "select trans from Transaction trans where trans.account= (select a.accountNo from Account a where a.accountNo=: accNo )";
		Query query = em.createQuery(jpql);
		query.setParameter("accNo", accountNo);
		List<Transaction> transactions = query.getResultList();
		return transactions;
	}
	
	
	@Override
	public List<Transaction> fetchTransactionByDate(StatementDto statementDto) {
		int customerId = statementDto.getCustomerId();
    	//Customer c = customerDao.findCustomerById(customerId);
		//fromAccount
    	int accNo = accDao.findAccountNoByCustomerId(customerId);
    	LocalDate fromDate = LocalDate.parse(statementDto.getFromDate());
    	LocalDate toDate = LocalDate.parse(statementDto.getToDate());
    	String jpql = "select t from Transaction t where t.account.accountNo =: acctNo AND t.transactionDate between :fDate and :tDate";
    	//String jpql = "select trans from Transaction trans where trans.account= (select a.accountNo from Account a where a.accountNo=: accountNo) AND trans.transactionDate between fromDate and toDate)";
    	Query query = em.createQuery(jpql);
    	query.setParameter("acctNo", accNo);
    	query.setParameter("fDate", fromDate);
    	query.setParameter("tDate", toDate);
    	List<Transaction> transactions = query.getResultList();
		return transactions;
	}
	
	@Override
	public Register getRegisterByAccountNo(int accountNo) {
		String jpql = "select r from Register r where r.account.accountNo =: accNo";
		Query query = em.createQuery(jpql);
    	query.setParameter("accNo", accountNo);
    	Register r = (Register)query.getSingleResult();
		return r;
	}
	
	
	//-----------------------------------------------------------------------//
	
	
	
	
	
	
	@Override
    @Transactional
	public Account addAccountWithMultipleTransactions(Account account) {
		Account acc = em.merge(account);	
		return acc;
	}
	@Override
	public List<Account> updateAccountBalanceOnTransaction(Beneficiary beneficiary, int amount) {
		// TODO Auto-generated method stub
		return null;
	}









	

	

	
	
	
	
	

//	@Override
//	public List<Account> updateAccountBalanceOnTransaction(Beneficiary beneficiary,int amount) {
//		
//		Account  fromAccount=beneficiary.getAccount();
//		Account acc = em.merge(fromAccount);
//		
//	
//		int fromAccountBalance=fromAccount.getBalance();
//		int fromBal=fromAccountBalance-amount;
//		fromAccount.setBalance(fromBal);
//		Account fa=accDao.addAccountWithAnExistingCustomer(fromAccount);
//		
//		Account toAccount=accDao.findAccountByAccountNo(beneficiary.getToAccountNo());
//		
//		int toAccountBalance=toAccount.getBalance();
//		int toBal=toAccountBalance+amount;
//		toAccount.setBalance(toBal);
//		Account ta=accDao.addAccountWithAnExistingCustomer(toAccount);
//		
//		
//		List<Account> accounts=new ArrayList<>();
//		accounts.add(acc);
//		accounts.add(fa);
//		accounts.add(ta);
//		
//		return accounts;
//		
//	}

	
}
