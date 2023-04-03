package com.flexi.lease.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.flexi.lease.model.Customer;
import com.flexi.lease.service.CustomersService;

import net.minidev.json.JSONObject;

@RunWith(MockitoJUnitRunner.class)
public class CustomersControllerTest {

	@InjectMocks
	private CustomersController customersController;

	@Mock
	private CustomersService customersService;

	private MockMvc mockMvc;

	private String customerRequestBodyCreate;

	private String customerRequestBodyUpdate;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(customersController).build();

		JSONObject customerObject = new JSONObject();
		customerObject.put("firstName", "John");
		customerObject.put("lastName", "Adams");
		customerObject.put("birthdate", "2000-02-03");

		customerRequestBodyCreate = customerObject.toString();

		customerObject = new JSONObject();
		customerObject.put("firstName", "Jane");
		customerObject.put("lastName", "Adams");
		customerObject.put("birthdate", "2000-02-03");

		customerRequestBodyUpdate = customerObject.toString();
	}

	@Test
	public void testGetAllCustomers() throws Exception {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null));
		customers.add(new Customer(2, "Jane", "Adams", new Date(System.currentTimeMillis()), "+49 18432378908", "Jane.adams@gmail.com", null));

		when(customersService.findAll()).thenReturn(customers);

		mockMvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].customerId", is(1))).andExpect(jsonPath("$[0].firstName", is("John")))
				.andExpect(jsonPath("$[0].lastName", is("Adams"))).andExpect(jsonPath("$[1].customerId", is(2)))
				.andExpect(jsonPath("$[1].firstName", is("Jane"))).andExpect(jsonPath("$[1].lastName", is("Adams")));

		verify(customersService, times(1)).findAll();
		verifyNoMoreInteractions(customersService);
	}

	@Test
	public void testCreateCustomer() throws Exception {
		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);

		when(customersService.saveCustomer(any(Customer.class))).thenReturn(customer);

		mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(customerRequestBodyCreate)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.customerId", is(1))).andExpect(jsonPath("$.firstName", is("John")))
				.andExpect(jsonPath("$.lastName", is("Adams")));

		verify(customersService, times(1)).saveCustomer(any(Customer.class));
		verifyNoMoreInteractions(customersService);
	}

	@Test
	public void testGetCustomer() throws Exception {
		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);

		when(customersService.findByCustomerId(anyInt())).thenReturn(customer);

		mockMvc.perform(get("/customers/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId", is(1))).andExpect(jsonPath("$.firstName", is("John")))
				.andExpect(jsonPath("$.lastName", is("Adams")));

		verify(customersService, times(1)).findByCustomerId(anyInt());
		verifyNoMoreInteractions(customersService);
	}

	@Test
	public void testUpdateCustomer() throws Exception {
		Customer existingCustomer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "adams@gmail.com",  null);
		Customer updatedCustomer = new Customer(1, "Jane", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "adams@gmail.com", null);

		when(customersService.findByCustomerId(anyInt())).thenReturn(existingCustomer);
		when(customersService.saveCustomer(any(Customer.class))).thenReturn(updatedCustomer);

		mockMvc.perform(put("/customers/1").contentType(MediaType.APPLICATION_JSON).content(customerRequestBodyUpdate)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId", is(1))).andExpect(jsonPath("$.firstName", is("Jane")))
				.andExpect(jsonPath("$.lastName", is("Adams")));

		verify(customersService, times(1)).findByCustomerId(anyInt());
	}

}
