package com.lti.repository;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Address;
import com.lti.entity.ApprovalStatus;
import com.lti.entity.Customer;
import com.lti.entity.Verification;


@Repository
public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public Customer addOrUpdateACustomer(Customer customer) {
		Customer persistedUser = em.merge(customer);
		return persistedUser;
	}


	@Override
	@Transactional
	public Customer addorUpdateCustomerWithAnAddress(Customer customer) {
		Customer c = em.merge(customer);
	    return c;
	}

	@Override
	@Transactional
	public Address addAddressToACustomer(Address address) {
//		String jpql = 'select c form Customer '
		Address add = em.merge(address);
		return add;
	}

	@Override
	public Address getCustomerAddressByCustomerId(int customerId) {

		Customer u = em.find(Customer.class, customerId);
		return u.getAddress();
	}

	@Override
	public Customer findCustomerById(int customerId) {
		Customer c = em.find(Customer.class, customerId);
		return c;
	}


	@Override
	public int findByEmailAndPassword(String email, int password) {
		return (Integer) em
				.createQuery("select c.id from Customer c where c.email = :em and c.password = :pw")
				.setParameter("em", email)
				.setParameter("pw", password)
				.getSingleResult();
	}


	@Override
	public int findLastAddedCustomer() {
		String jpql = "select max(c.customerId) from Customer c";
		Query query = em.createQuery(jpql);
		int cid = (int) query.getSingleResult();
		return cid;
	}


	@Override
	public boolean isCustomerPresent(String email) {
		return (Long)
				em
				.createQuery("select count(c.id) from Customer c where c.email = :em")
				.setParameter("em", email)
				.getSingleResult() == 1 ? true : false;
	}


	@Override
	public ApprovalStatus customerVerification(int customerId) {
		Customer c = findCustomerById(customerId);
		String panCard = c.getPanCard();
		String jpql = "select count(c.panCard) from Customer c where c.panCard =: pan";
		Query query = em.createQuery(jpql);
		query.setParameter("pan", panCard);
		long count = (long) query.getSingleResult();
		if(count==1) {
			c.setStatus(ApprovalStatus.APPROVED);
		}
		else {
			c.setStatus(ApprovalStatus.REJECTED);
		}
		return c.getStatus();
	}


	@Override
	public List<Customer> viewAllCustomer() {
		String jpql = "select c from Customer c where c.status = 2";
		Query query = em.createQuery(jpql);
		List<Customer> customers = (List<Customer>) query.getResultList();
		return customers;
	}


	@Override
	public List<Customer> viewAllCustomerAdmin() {
		String jpql = "select c from Customer c where c.status = 0";
		Query query = em.createQuery(jpql);
		List<Customer> customers = (List<Customer>) query.getResultList();
		return customers;
	}





}
