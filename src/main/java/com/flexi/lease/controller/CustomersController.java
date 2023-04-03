package com.flexi.lease.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flexi.lease.model.Customer;
import com.flexi.lease.service.CustomersService;

/**
 * This controller handles the CRUD operations for Customers.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/customers")
public class CustomersController {

	private static final Logger logger = LoggerFactory.getLogger(CustomersController.class);

	@Autowired
	private CustomersService customersService;

	/**
	 * 
	 * HTTP GET request handler to fetch all customers.
	 * 
	 * @return ResponseEntity with a list of customers and HTTP status cod 200.
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> getAllCustomers() {
		logger.info("In getAllCustomers(), endpoint : /customers");
		return new ResponseEntity<>(customersService.findAll(), HttpStatus.OK);
	}

	/**
	 * 
	 * HTTP POST request handler to create a new customer.
	 * 
	 * @param customer the customer object to be created.
	 * @return ResponseEntity with the created customer object and HTTP status code
	 *         201.
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		logger.info("In createCustomer(), endpoint : /customers");
		try {
			return new ResponseEntity<>(customersService.saveCustomer(customer), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Exception while creating the Customer: " + e.getStackTrace());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * HTTP GET request handler to fetch a customer by customer Id.
	 * 
	 * @param customerId the Id of the customer to be fetched.
	 * 
	 * @return ResponseEntity with the fetched customer object and HTTP status code
	 *         200.
	 */
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomer(@PathVariable Integer customerId) {
		logger.info("In getCustomer(), endpoint : /customers" + customerId);
		Customer customer = customersService.findByCustomerId(customerId);

		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			logger.error("Customer does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * HTTP PUT request handler to update an existing customer.
	 * 
	 * @param customerId      the Id of the customer to be updated.
	 * 
	 * @param customerRequest the updated customer object.
	 * 
	 * @return ResponseEntity with the updated customer object and HTTP status code
	 *         200.
	 */
	@RequestMapping(value = "/{customerId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> updateCustomer(@PathVariable Integer customerId,
			@RequestBody Customer customerRequest) {
		logger.info("In updateCustomer(), endpoint : /customers/" + customerId);
		Customer customer = customersService.findByCustomerId(customerId);

		if (customer != null) {
			return new ResponseEntity<>(
					customersService.saveCustomer(
							new Customer(customerId, customerRequest.getFirstName(), customerRequest.getLastName(),
									customerRequest.getBirthdate(), customerRequest.getContactNumber(),
									customerRequest.getEmailId(), customerRequest.getLeasingContracts())),
					HttpStatus.OK);
		} else {
			logger.error("Customer does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * HTTP DELETE request handler to delete an existing customer.
	 * 
	 * @param customerId the Id of the customer to be deleted.
	 * @return ResponseEntity with HTTP status code 204.
	 */
	@RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Integer customerId) {
		logger.info("In deleteCustomer(), endpoint : /customers/" + customerId);
		try {
			customersService.deleteByCustomerId(customerId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("Exception while deleting the customer: " + e.getStackTrace());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
