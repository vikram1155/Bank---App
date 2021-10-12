package com.lti.service;

import java.util.List;

import com.lti.entity.Address;
import com.lti.entity.ApprovalStatus;
import com.lti.entity.Customer;

public interface CustomerService {
	public Customer addOrUpdateACustomer(Customer customer);

	public Customer addorUpdateCustomerWithAnAddress(Customer customer);

	public Address addAddressToACustomer(Address address);

	public Address getCustomerAddressByCustomerId(int customerId);

	public Customer findCustomerById(int customerId);
	
	public Customer login(String email, int password);
	
	public int findLastAddedCustomer();
	
	public ApprovalStatus customerVerification(int customerId);
	
	public List<Customer> viewAllCustomer();
	
	//admin viewing the customers
	public List<Customer> viewAllCustomerAdmin();
	
	public void forgetPassword(int customerId);
	
}
