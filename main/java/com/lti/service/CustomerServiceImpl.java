package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lti.repository.CustomerDao;
import com.lti.entity.Address;
import com.lti.entity.ApprovalStatus;
import com.lti.entity.Customer;
import com.lti.exception.CustomerServiceException;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerDao customerDao;

	@Autowired
	private EmailService emailService;
	
	@Override
	public Customer addOrUpdateACustomer(Customer customer) {
//		String text="Your Account registration is in process. Please wait for approval.";
//		String subject="Account creation in process";
//		emailService.sendEmailForNewRegistration(customer.getEmail(), text, subject);
		return  customerDao.addOrUpdateACustomer(customer);
	}
	@Override
	public Customer addorUpdateCustomerWithAnAddress(Customer customer) {
		
		return customerDao.addorUpdateCustomerWithAnAddress(customer);
	}

	@Override
	public Address addAddressToACustomer(Address address) {
		
		return customerDao.addAddressToACustomer(address);
	}

	@Override
	public Address getCustomerAddressByCustomerId(int customerId) {
		
		return customerDao.getCustomerAddressByCustomerId(customerId);
	}

	@Override
	public Customer findCustomerById(int customerId) {
		
		return customerDao.findCustomerById(customerId);
	}
	
	@Override
	public Customer login(String email, int password) {
		try {
			if(!customerDao.isCustomerPresent(email))
				throw new CustomerServiceException("Customer not registered!");
			int id = customerDao.findByEmailAndPassword(email, password);
			Customer customer = customerDao.findCustomerById(id);
			return customer;
		}
		catch(EmptyResultDataAccessException e) {
			throw new CustomerServiceException("Incorrect email/password");
		}
	}
	
	@Override
	public int findLastAddedCustomer() {
		// TODO Auto-generated method stub
		return customerDao.findLastAddedCustomer();
	}
	@Override
	public ApprovalStatus customerVerification(int customerId) {
	
		return customerDao.customerVerification(customerId);
	}
	
	
	@Override
	public List<Customer> viewAllCustomer() {
		return customerDao.viewAllCustomer();
	}
	
	
	@Override
	public List<Customer> viewAllCustomerAdmin() {
		return customerDao.viewAllCustomerAdmin();
	}
	
	@Override
	public void forgetPassword(int customerId) {
		Customer c = customerDao.findCustomerById(customerId);
		int password  = c.getPassword();
		int newPassword = password + 10;
		c.setPassword(newPassword);
		customerDao.addOrUpdateACustomer(c);
		String email = c.getEmail();
		String text = "Your new password is :" +newPassword;
		String subject = "New Password";
		emailService.sendEmailForNewRegistration(email, text, subject);	
	}


}
