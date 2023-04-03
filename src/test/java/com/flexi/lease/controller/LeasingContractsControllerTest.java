package com.flexi.lease.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.flexi.lease.model.LeasingContract;
import com.flexi.lease.model.Vehicle;
import com.flexi.lease.service.LeasingContractsService;

import net.minidev.json.JSONObject;

@RunWith(MockitoJUnitRunner.class)
public class LeasingContractsControllerTest {

	@InjectMocks
	private LeasingContractsController leasingContractsController;

	@Mock
	private LeasingContractsService leasingContractsService;

	private MockMvc mockMvc;

	private String leasingContractRequestBodyCreate;

	private String leasingContractRequestBodyUpdate;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(leasingContractsController).build();

		JSONObject leasingContractObject = new JSONObject();
		leasingContractObject.put("customerId", 1);
		leasingContractObject.put("vehicleId", 1);
		leasingContractObject.put("startDate", "2023-04-04");
		leasingContractObject.put("endDate", "2024-01-01");

		leasingContractRequestBodyCreate = leasingContractObject.toString();

		leasingContractObject = new JSONObject();
		leasingContractObject.put("customerId", 2);
		leasingContractObject.put("vehicleId", 2);
		leasingContractObject.put("startDate", "2023-04-04");
		leasingContractObject.put("endDate", "2024-02-01");

		leasingContractRequestBodyUpdate = leasingContractObject.toString();
	}

	@Test
	public void testGetAllLeasingContracts() throws Exception {
		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);
		
		Date startDate = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, 10);
		Date endDate = calendar.getTime();
		
		List<LeasingContract> leasingContracts = new ArrayList<>();
		leasingContracts.add(new LeasingContract(1, 1000, startDate, endDate, customer, vehicle));
		leasingContracts.add(new LeasingContract(2, 2000, startDate, endDate, customer, vehicle));

		when(leasingContractsService.findAll()).thenReturn(leasingContracts);

		mockMvc.perform(get("/leasing-contracts").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].contractNumber", is(1))).andExpect(jsonPath("$[0].monthlyRate", is(1000.0)));

		verify(leasingContractsService, times(1)).findAll();
		verifyNoMoreInteractions(leasingContractsService);
	}

	@Test
	public void testGetLeasingContractById() throws Exception {
		LeasingContract leasingContract = new LeasingContract();
		leasingContract.setContractNumber(1);
		leasingContract.setMonthlyRate(1000);

		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);

		leasingContract.setCustomer(customer);
		leasingContract.setVehicle(vehicle);

		when(leasingContractsService.findByContractNumber(1)).thenReturn(leasingContract);

		mockMvc.perform(get("/leasing-contracts/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.contractNumber", is(1))).andExpect(jsonPath("$.monthlyRate", is(1000.0)));

		verify(leasingContractsService).findByContractNumber(1);
		verifyNoMoreInteractions(leasingContractsService);
	}

	@Test
	public void testCreateLeasingContract() throws Exception {
		// Create a new leasing contract
		LeasingContract leasingContract = new LeasingContract();
		leasingContract.setContractNumber(1);
		leasingContract.setMonthlyRate(1000);

		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);

		leasingContract.setCustomer(customer);
		leasingContract.setVehicle(vehicle);

		// Set up mock service behavior
		when(leasingContractsService.saveLeasingContract(any(LeasingContract.class))).thenReturn(leasingContract);

		// Send a POST request to create the leasing contract
		mockMvc.perform(post("/leasing-contracts").contentType(MediaType.APPLICATION_JSON)
				.content(leasingContractRequestBodyCreate).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.contractNumber", is(1)))
				.andExpect(jsonPath("$.monthlyRate", is(1000.0)));

		// Verify that the service method was called
		verify(leasingContractsService, times(1)).saveLeasingContract(any(LeasingContract.class));
		verifyNoMoreInteractions(leasingContractsService);
	}

	@Test
	public void testUpdateLeasingContract() throws Exception {
		LeasingContract existingLeasingContract = new LeasingContract();
		existingLeasingContract.setContractNumber(1);
		existingLeasingContract.setMonthlyRate(1000);
		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);
		existingLeasingContract.setCustomer(customer);
		existingLeasingContract.setVehicle(vehicle);

		LeasingContract updatedLeasingContract = new LeasingContract();
		updatedLeasingContract.setContractNumber(1);
		updatedLeasingContract.setMonthlyRate(2000);
		updatedLeasingContract.setCustomer(customer);
		updatedLeasingContract.setVehicle(vehicle);

		when(leasingContractsService.findByContractNumber(anyInt())).thenReturn(existingLeasingContract);
		when(leasingContractsService.saveLeasingContract(any(LeasingContract.class)))
				.thenReturn(updatedLeasingContract);

		mockMvc.perform(put("/leasing-contracts/1").contentType(MediaType.APPLICATION_JSON)
				.content(leasingContractRequestBodyUpdate).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.contractNumber", is(1)))
				.andExpect(jsonPath("$.monthlyRate", is(2000.0)));

		verify(leasingContractsService, times(1)).findByContractNumber(anyInt());
		verify(leasingContractsService, times(1)).saveLeasingContract(any(LeasingContract.class));
		verifyNoMoreInteractions(leasingContractsService);
	}

	@Test
	public void testDeleteLeasingContract() throws Exception {
		// Create a leasing contract
		LeasingContract leasingContract = new LeasingContract();
		leasingContract.setContractNumber(1);
		leasingContract.setMonthlyRate(1000);

		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);

		leasingContract.setCustomer(customer);
		leasingContract.setVehicle(vehicle);

		// Stub the deleteLeasingContract method to return void
		doNothing().when(leasingContractsService).deleteByContractNumber(leasingContract.getContractNumber());

		// Send a DELETE request to delete the leasing contract
		mockMvc.perform(delete("/leasing-contracts/{id}", leasingContract.getContractNumber())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		// Verify that the deleteByContractNumber method was called exactly once with the correct id
		verify(leasingContractsService, times(1)).deleteByContractNumber(leasingContract.getContractNumber());
		verifyNoMoreInteractions(leasingContractsService);
	}

}
