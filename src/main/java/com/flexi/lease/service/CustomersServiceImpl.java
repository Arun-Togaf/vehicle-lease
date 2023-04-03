package com.flexi.lease.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flexi.lease.model.Customer;
import com.flexi.lease.repository.CustomersRepository;

/**
 * 
 * Service class that implements the CustomersService interface and provides
 * methods for CRUD operations on customer data.
 */
@Service
public class CustomersServiceImpl implements CustomersService {

	@Autowired
	private CustomersRepository customersRepository;

	/**
	 * Returns a list of all customers in the database.
	 * 
	 * @return List<Customer>
	 */
	@Override
	public List<Customer> findAll() {
		return customersRepository.findAll();
	}

	/**
	 * Saves the given customer to the database.
	 * 
	 * @param customer
	 * @return Customer
	 */
	@Override
	public Customer saveCustomer(Customer customer) {
		return customersRepository.save(customer);
	}

	/**
	 * Deletes the customer with the given ID from the database.
	 * 
	 * @param customerId
	 */
	@Override
	public void deleteByCustomerId(Integer customerId) {
		customersRepository.deleteById(customerId);
	}

	/**
	 * Returns the customer with the given Id from the database
	 * 
	 * @param customerId
	 * @return Customer
	 */
	@Override
	public Customer findByCustomerId(Integer customerId) {
		return customersRepository.findById(customerId).orElse(null);
	}

}
