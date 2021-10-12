package com.lti.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Account;
import com.lti.entity.Customer;

@Repository
public class AccountDaoImpl implements AccountDao{
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public Account addAccountWithAnExistingCustomer(Account account) {
		Account acc = em.merge(account);
		return acc;
	}
	
	@Override
	public List<Account> getAllAccountsOfACustomer(int customerId) {
		Customer c = em.find(Customer.class, customerId);
		return c.getAccount();
	}
	
	@Override
	public Customer findCustomerByAccountNo(int accountNo) {
		Account u = em.find(Account.class, accountNo);
		return u.getCustomer();
	}

	@Override
	public Account findAccountByAccountNo(int accountNo) {
		Account a = em.find(Account.class, accountNo);
		return a;
	}
	
	@Override
	public int findAccountNoByCustomerId(int customerId) {
		List<Account> a =  getAllAccountsOfACustomer(customerId);
		int accnos[] = new int[3];
		for(Account i: a) {
			accnos[0] = i.getAccountNo();
		}
		int accNo =  accnos[0];
		System.out.println(accNo);
		return accNo;
	}

	
	
	//--------------------------------------------------------------------------------//
	
	@Override
	@Transactional
	public Customer addNewCustomerWithOneAccount(Customer customer) {
	Customer c = em.merge(customer);
	return c;
	}

	@Override
	@Transactional
	public Customer addNewCustomerWithMultipleAccounts(Customer customer) {
		Customer c = em.merge(customer);
		return c;
	}

	@Override
	@Transactional
	public Customer addAccountsToAnExistingCustomer(Customer customer) {
		Customer a = em.merge(customer);	
		return a;

	}

	

	
	


	




}
