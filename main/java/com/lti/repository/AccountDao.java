package com.lti.repository;

import java.util.List;

import com.lti.entity.Account;
import com.lti.entity.Customer;

public interface AccountDao {

	/*
	 * viewAccountDetails(); viewAccountStatement();
	 * 
	 */
	public Customer addNewCustomerWithOneAccount(Customer customer);

	public Customer addNewCustomerWithMultipleAccounts(Customer customer);

	public Customer addAccountsToAnExistingCustomer(Customer customer); 
	
	//1st method
	public Account addAccountWithAnExistingCustomer(Account account);

	//4th method
	public List<Account> getAllAccountsOfACustomer(int customerId);  //viewAccount details

	//2nd method
	public Customer findCustomerByAccountNo(int accountNo);
	
	//3rd method
	public Account findAccountByAccountNo(int accountNo);
	
	public int findAccountNoByCustomerId(int customerId);
	
//	public Account findAccountByCustomerId(int customerId);
	
}
