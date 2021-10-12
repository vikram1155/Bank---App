package com.lti.service;

import java.util.List;

import com.lti.entity.Account;
import com.lti.entity.Customer;

public interface AccountService {
	
	public List<Account> getAllAccountsOfACustomer(int customerId);

	public Customer findCustomerByAccountNo(int accountId);
	
	Account addAccountWithAnExistingCustomer(Account account, Customer customer);
	
	Account findAccountByAccountNo(int accountNo);
	
	Account addAccountWithAnExistingCustomer(Account account);
	
	public int findAccountNoByCustomerId(int customerId);
	
	
	//----------------------------------------------------------------------------------------------//
	
	
	public Customer addNewCustomerWithOneAccount(Customer customer);

	public Customer addNewCustomerWithMultipleAccounts(Customer customer);

	public Customer addAccountsToAnExistingCustomer(Customer customer);  //_____________doubt

}
