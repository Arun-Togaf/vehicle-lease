package com.flexi.lease.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.flexi.lease.model.LeasingContract;
import com.flexi.lease.model.Vehicle;
import com.flexi.lease.repository.LeasingContractsRepository;

@RunWith(MockitoJUnitRunner.class)
public class LeasingContractsServiceImplTest {

	@Mock
	private LeasingContractsRepository leasingContractsRepository;

	@InjectMocks
	private LeasingContractsServiceImpl leasingContractsService;

	@Test
	public void testFindAllLeasingContracts() {
		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);
		
		Date startDate = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, 10);
		Date endDate = calendar.getTime();
		
		List<LeasingContract> expectedLeasingContracts = new ArrayList<>();
		expectedLeasingContracts.add(new LeasingContract(1, 1000, startDate, endDate, customer, vehicle));
		expectedLeasingContracts.add(new LeasingContract(2, 2000, startDate, endDate, customer, vehicle));
		Mockito.when(leasingContractsRepository.findAll()).thenReturn(expectedLeasingContracts);
		List<LeasingContract> actualLeasingContracts = leasingContractsService.findAll();
		assertEquals(expectedLeasingContracts, actualLeasingContracts);
	}

	@Test
	public void testFindByLeasingContractId() {
		LeasingContract expectedLeasingContract = new LeasingContract();
		expectedLeasingContract.setContractNumber(1);
		expectedLeasingContract.setMonthlyRate(1000);

		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);

		expectedLeasingContract.setCustomer(customer);
		expectedLeasingContract.setVehicle(vehicle);

		Mockito.when(leasingContractsRepository.findById(1)).thenReturn(Optional.of(expectedLeasingContract));
		LeasingContract actualLeasingContract = leasingContractsService.findByContractNumber(1);
		assertEquals(expectedLeasingContract, actualLeasingContract);
	}

	@Test
	public void testCreateLeasingContract() {
		// Create a new leasing contract
		LeasingContract leasingContractToSave = new LeasingContract();
		leasingContractToSave.setContractNumber(1);
		leasingContractToSave.setMonthlyRate(1000);

		Customer customer = new Customer(1, "John", "Adams", new Date(System.currentTimeMillis()), "+49 13432378908", "John.adams@gmail.com", null);
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);

		leasingContractToSave.setCustomer(customer);
		leasingContractToSave.setVehicle(vehicle);
		Mockito.when(leasingContractsRepository.save(leasingContractToSave)).thenReturn(leasingContractToSave);
		LeasingContract savedLeasingContract = leasingContractsService.saveLeasingContract(leasingContractToSave);
		assertEquals(leasingContractToSave, savedLeasingContract);
	}

	@Test
	public void testUpdateLeasingContract() {
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

		Mockito.when(leasingContractsRepository.save(updatedLeasingContract)).thenReturn(updatedLeasingContract);

		LeasingContract result = leasingContractsService.saveLeasingContract(updatedLeasingContract);

		Mockito.verify(leasingContractsRepository, Mockito.times(1)).save(updatedLeasingContract);
		assertEquals(updatedLeasingContract, result);
	}

	@Test
	public void testDeleteByLeasingContractId() {
		Integer leasingContractId = 1;
		leasingContractsService.deleteByContractNumber(leasingContractId);
		Mockito.verify(leasingContractsRepository, Mockito.times(1)).deleteById(leasingContractId);
	}

}
