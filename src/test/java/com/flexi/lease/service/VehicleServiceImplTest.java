package com.flexi.lease.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.flexi.lease.model.Vehicle;
import com.flexi.lease.repository.VehiclesRepository;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceImplTest {

	@Mock
	private VehiclesRepository vehiclesRepository;

	@InjectMocks
	private VehiclesServiceImpl vehiclesService;

	@Test
	public void testFindAllVehicles() {
		List<Vehicle> expectedVehiclesList = new ArrayList<>();
		expectedVehiclesList.add(new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null));
		expectedVehiclesList.add(new Vehicle(2, "Honda", "Accord", 2021, "1234567891", 15000.0, null));
		Mockito.when(vehiclesRepository.findAll()).thenReturn(expectedVehiclesList);
		List<Vehicle> actualVehicles = vehiclesService.findAll();
		assertEquals(expectedVehiclesList, actualVehicles);
	}

	@Test
	public void testSaveVehicle() {
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);
		Mockito.when(vehiclesRepository.save(vehicle)).thenReturn(vehicle);
		Vehicle savedVehicle = vehiclesService.saveVehicle(vehicle);
		assertEquals(vehicle, savedVehicle);
	}

	@Test
	public void testDeleteByVehicleId() {
		Integer vehicleId = 1;
		vehiclesService.deleteByVehicleId(vehicleId);
		Mockito.verify(vehiclesRepository, Mockito.times(1)).deleteById(vehicleId);
	}

	@Test
	public void testFindByVehicleId() {
		Vehicle expectedVehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);
		Mockito.when(vehiclesRepository.findById(1)).thenReturn(Optional.of(expectedVehicle));
		Vehicle actualVehicle = vehiclesService.findByVehicleId(1);
		assertEquals(expectedVehicle, actualVehicle);
	}

}
