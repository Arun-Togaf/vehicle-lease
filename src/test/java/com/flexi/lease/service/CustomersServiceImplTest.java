package com.flexi.lease.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.flexi.lease.model.Customer;
import com.flexi.lease.repository.CustomersRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomersServiceImplTest {

	@Mock
	private CustomersRepository customersRepository;

	@InjectMocks
	private CustomersServiceImpl customersService;

	@Test
	public void testFindAll() {
		List<Customer> expectedCustomers = new ArrayList<>();
		expectedCustomers.add(new Customer(1, "John", "Doe", new Date(System.currentTimeMillis()), "+49 13432378908",
				"John.adams@gmail.com", null));
		expectedCustomers.add(new Customer(2, "Jane", "Doe", new Date(System.currentTimeMillis()), "+49 73432378908",
				"Jane.adams@gmail.com", null));
		Mockito.when(customersRepository.findAll()).thenReturn(expectedCustomers);
		List<Customer> actualCustomers = customersService.findAll();
		assertEquals(expectedCustomers, actualCustomers);
	}

	@Test
	public void testSaveCustomer() {
		Customer customerToSave = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()),
				"+49 13432378908", "John.adams@gmail.com", null);
		Mockito.when(customersRepository.save(customerToSave)).thenReturn(customerToSave);
		Customer savedCustomer = customersService.saveCustomer(customerToSave);
		assertEquals(customerToSave, savedCustomer);
	}

	@Test
	public void testDeleteByCustomerId() {
		Integer customerId = 1;
		customersService.deleteByCustomerId(customerId);
		Mockito.verify(customersRepository, Mockito.times(1)).deleteById(customerId);
	}

	@Test
	public void testFindByCustomerId() {
		Integer customerId = 1;
		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908",
				"John.adams@gmail.com", null);
		Mockito.when(customersRepository.findById(customerId)).thenReturn(Optional.of(customer));
		Customer actualCustomer = customersService.findByCustomerId(customerId);
		assertEquals(customer, actualCustomer);
	}

}
