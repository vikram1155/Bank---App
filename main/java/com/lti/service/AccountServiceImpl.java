package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.repository.AccountDao;
import com.lti.repository.CustomerDao;
import com.lti.entity.Account;
import com.lti.entity.Customer;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountDao accountDao;

	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public Customer addNewCustomerWithOneAccount(Customer customer) {
	
		return accountDao.addNewCustomerWithOneAccount(customer);
	}

	@Override
	public Customer addNewCustomerWithMultipleAccounts(Customer customer) {
		
		return accountDao.addNewCustomerWithMultipleAccounts(customer);
	}

	@Override
	public Customer addAccountsToAnExistingCustomer(Customer customer) {
		
		return accountDao.addAccountsToAnExistingCustomer(customer);
	}

	@Override
	public List<Account> getAllAccountsOfACustomer(int customerId) {
		
		return accountDao.getAllAccountsOfACustomer(customerId);
	}

	
//-----------------------our methods---------------------------------------------
	@Override
	public Account addAccountWithAnExistingCustomer(Account account, Customer customer) {
		int password = customer.getPassword();
		int id = customer.getCustomerId();
		//int accNo = account.getAccountNo();
		String text="Successfully registered. Your id is "+id+ ".  Your password is "+password;
		String subject="Registration Confirmation";
		emailService.sendEmailForNewRegistration(customer.getEmail(), text, subject);
		return accountDao.addAccountWithAnExistingCustomer(account);
	}
	
	@Override
	public Customer findCustomerByAccountNo(int accountId) {
	
		return accountDao.findCustomerByAccountNo(accountId);
	}
	@Override
	public Account findAccountByAccountNo(int accountNo) {
		
		return accountDao.findAccountByAccountNo(accountNo);
	}

	@Override
	public Account addAccountWithAnExistingCustomer(Account account) {
		return accountDao.addAccountWithAnExistingCustomer(account);
	}

	@Override
	public int findAccountNoByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		return accountDao.findAccountNoByCustomerId(customerId);
	}

}
