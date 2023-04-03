package com.flexi.lease.service;

import java.util.List;

import com.flexi.lease.model.Customer;

/**
 * 
 * This interface provides the methods for accessing and manipulating customer
 * information.
 */
public interface CustomersService {

	List<Customer> findAll();

	Customer saveCustomer(Customer customer);

	void deleteByCustomerId(Integer customerId);

	Customer findByCustomerId(Integer customerId);

}
